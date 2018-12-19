'use strict';


var messageForm = document.querySelector('#messageForm');
var messageButton = document.querySelector('#messageButton');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');
var messageUserList = document.querySelector('#userSelector');

var stompClient = null;
var username = null;
var usercolor = "green";

function connect() {
    username = document.querySelector('#username').innerText.trim();
    usercolor = document.querySelector('#username').style.color.trim();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

// Connect to WebSocket Server.
connect();

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/publicChatRoom', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, usercolor: usercolor, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var selectedUser = messageUserList.value
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            usercolor: usercolor,
            recepient: selectedUser,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');
    console.log(message);
    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' подключился!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' вышел из чата!';
    } else {
        messageElement.classList.add('chat-message');
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);

        var recepientElement = document.createElement('strong');
        recepientElement.classList.add('nickname');
        var recepientText = document.createTextNode(message.recepient);
        recepientElement.appendChild(recepientText);

        var timeElement = document.createElement('span');
        timeElement.classList.add('nickname');
        var timeText = document.createTextNode(message.time);
        timeElement.appendChild(timeText);


        messageElement.appendChild(timeElement);
        messageElement.appendChild(usernameElement);
        messageElement.appendChild(recepientElement);
    }

    var textElement = document.createElement('span');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    textElement.style.color = message.usercolor;

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


messageForm.addEventListener('submit', sendMessage, true);


