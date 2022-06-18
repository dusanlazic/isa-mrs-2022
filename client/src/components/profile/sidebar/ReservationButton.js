import { useState } from "react";
import CustomerReservationModal from "../../modals/reservation/CustomerReservationModal";

const ReservationButton = ({data}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  return ( 
    <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <button className="rounded-xl shadow-sm px-6 py-2 text-white font-bold 
        bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
        w-full" onClick={() => setIsModalOpen(true)}>
          Make a reservation
        </button>

        {isModalOpen &&
          <CustomerReservationModal data={data} close={() => setIsModalOpen(false)}/>
        }
      </div>
   );
}
 
export default ReservationButton;