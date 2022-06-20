import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import QuickReservation from "./QuickReservation";

const QuickReservationList = ({data}) => {
	const [reservations, setReservations] = useState(null);

  useEffect(() => {
    fetchData();
  }, [])
  
  const fetchData = () => {
		get(`/api/ads/${data.id}/quick-reservations`)
    .then((response) => {
			console.log(response.data);
			setReservations(response.data);
    });
  }

  if (reservations === null) {
    return null;
  }

  return ( 
    <div>
      { reservations.length === 0 &&
        <div className="flex flex-col">
          <div>Advertisement has no discounts.</div>
        </div>
      }
      { reservations.length > 0 &&
        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-3 xl:grid-cols-4 gap-x-2 lg:gap-x-6 gap-y-4">
        {reservations.map(reservation => 
          <div key={reservation.id}>
            <QuickReservation advertisement={data} reservation={reservation}/>
          </div>
          
        )}
      </div>
      }
    </div>
   );
}
 
export default QuickReservationList;