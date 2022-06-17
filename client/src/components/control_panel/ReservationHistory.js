import { useEffect, useState } from "react"
import { useParams, useNavigate } from 'react-router-dom'
import { get } from "../../adapters/xhr";
import ReservationItem from "./ReservationItem";

const ReservationHistory = () => {

  const [reservations, setReservations] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    get(`/api/advertisers/reservations`)
      .then(response => {
        setReservations(response.data);
      })
      .catch(error => {
        navigate('/notfound');
      });
  }, [])

  return (
    <div className="grid 2xl:grid-cols-2 gap-4 mx-auto justify-items-center">
      {reservations != null && reservations.map(reservation =>
        <div key={reservation.id} className="w-full">
          <ReservationItem reservation={reservation} />
        </div>
      )}
    </div>

  );
}

export default ReservationHistory;