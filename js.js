//Tomar 3 argumentos de una lista de pedidos
// orderList, orderId, status = "procesando" o "Entregado"
// orderList = {orderId: "123", status: "procesando"}
//actualizar segun el status y devolver el objeto actualizado
// Si el status es entregado, se elimina el pedido de la lista
//Si el oirderId no existe, devolver la lista sin cambios
// el status por defecto es recibido

const updateOrder = (orderList, orderId, status = "recibido") => {
    const order = orderList.find((order) => order.orderId === orderId);
    if (order) {
        order.status = status;
        if (status === "entregado") {
            orderList = orderList.filter((order) => order.orderId !== orderId);
        }
    }
    return orderList;
};

console.log(proccessOrderList(orderList, 123, "entregado"))