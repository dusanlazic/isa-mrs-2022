const ReservationItem = ({ reservation }) => {
  return (
    <div className="flex text-left bg-gray-50 p-4 rounded-md hover:bg-gray-100 transition-colors">
      <img src={reservation.advertisement.photo == null ? "" : `/api/${reservation.advertisement.photo.uri}`} alt="" className="flex-none w-48 h-32 object-cover rounded-lg" />
      <div className="block ml-2 w-full">
        <div className="flex justify-between text-xl w-full text-gray-700 tracking-tight my-auto text-left leading-5">
          <h1>
            {reservation.advertisement.title}
          </h1>
          <h1 className="font-mono font-medium text-gray-700">
            {reservation.calculatedPrice}€
          </h1>
        </div>
        <div className="text-sm text-gray-500 pt-2"><b>From:</b> {reservation.startDateTime} <br></br> <b>To:</b> {reservation.endDateTime} </div>

        <div className="flex text-xl w-full text-gray-700 tracking-tight my-auto text-left leading-5 pt-2">
          <h1 className="font-mono text-md text-gray-700">
            {reservation.customer.firstName} {reservation.customer.lastName}
          </h1>
        </div>

      </div>
    </div>

  );
}

export default ReservationItem;