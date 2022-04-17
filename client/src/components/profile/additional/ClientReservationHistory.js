import ClientReservationItem from "./ClientReservationItem";

const reservations = [
  {
    id: 0,
    name: 'Praskozorje Vikendica',
    image: '/images/property_placeholder.jpg',
    startDate: '11/09/2001',
    endDate: '12/09/2001',
    price: 200
  },
  {
    id: 1,
    name: 'Ide gas',
    image: '/images/property_placeholder.jpg',
    startDate: '11/09/2001',
    endDate: '12/09/2001',
    price: 150
  },
  {
    id: 2,
    name: 'Pucaj mi u lobanju - vikendica',
    image: '/images/property_placeholder.jpg',
    startDate: '11/09/2001',
    endDate: '12/09/2001',
    price: 320
  },
]

const ClientReservationHistory = () => {
  return ( 
    <div className="flex flex-col gap-y-4">
      {reservations.map(reservation => 
        <div key={reservation.id}>
          <ClientReservationItem reservation={reservation}/>
        </div>
      )}
    </div>
   );
}
 
export default ClientReservationHistory;