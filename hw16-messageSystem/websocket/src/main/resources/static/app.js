let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/clients', (message) => {
            $("#clients tr").remove();
            let clients = JSON.parse(message.body);
            for (var i = 0; i < clients.length; i++) {
                addClient(clients[i]);
            }
        });

        stompClient.subscribe('/topic/clients/new', (message) => {
            let clients = JSON.parse(message.body);
            for (var i = 0; i < clients.length; i++) {
                addClient(clients[i]);
            }
        });
        clientsList();
    });
}

const clientsList = () => stompClient.send("/app/clients", {}, {})

const saveClient = () => stompClient.send("/app/clients/save", {}, JSON.stringify({
    'name': $("#name").val(),
    'address':$("#address").val(),
    'phones':[$("#phone1").val(),$("#phone2").val()]
}))

const addClient = (client) => {

    $("#clients").append(
        "<tr>" +
            "<td>" + client.id + "</td>"+
            "<td>" + client.name + "</td>"+
            "<td>" + client.address + "</td>"+
            "<td>" + client.phones + "</td>"+
        "</tr>"
    );
}
window.onload = function () {
   connect();
};

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#save").click(saveClient);
});
