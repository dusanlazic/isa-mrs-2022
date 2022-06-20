import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import { getSession } from "../../../contexts";
import QuickReservation from "./QuickReservation";

let showBookButton = false;
const QuickReservationList = ({data}) => {
	const [quickReservations, setQuickReservations] = useState(null);

  useEffect(() => {
    fetchData();
  }, [])
  
  const fetchData = () => {
		get(`/api/ads/${data.id}/quick-reservations`)
    .then((response) => {
			console.log(response.data);
			setQuickReservations(response.data);
      const session = getSession();
      if (session && session.accountType === 'CUSTOMER') showBookButton = true;
    });
  }

  if (quickReservations === null) {
    return null;
  }

  return ( 
    <div>
      { quickReservations.length === 0 &&
        <div className="flex flex-col">
          <div>Advertisement has no discounts.</div>
        </div>
      }
      { quickReservations.length > 0 &&
        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-3 xl:grid-cols-4 gap-x-2 lg:gap-x-6 gap-y-4">
        {quickReservations.map(quickReservation => 
          <div key={quickReservation.id}>
            <QuickReservation advertisement={data} quickReservation={quickReservation} 
            showBookButton={showBookButton}/>
          </div>
          
        )}
      </div>
      }
    </div>
   );
}
 
export default QuickReservationList;