const ClientReservationItem = ({reservation}) => {
  return ( 
  <div className="flex text-left">
    <img src={reservation.image} alt="" className="flex-none w-20 h-14 object-cover rounded-lg" />
    <div className="block ml-2 w-full">
      <div className="flex justify-between text-xl w-full text-gray-700 tracking-tight my-auto text-left leading-5">
        <h1>
          {reservation.name}
        </h1>
        <h1 className="font-mono font-medium text-gray-700">
          {reservation.price}€
        </h1>
      </div>
      <div className="text-sm text-gray-500">From: {reservation.startDate} To: {reservation.endDate} </div>
    </div>
  </div>
  );
}
 
export default ClientReservationItem;