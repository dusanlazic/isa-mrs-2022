import { useState } from "react";
import moment from "moment";
import { Icon } from "@iconify/react";

import BookQuickReservationModal from "../../modals/reservation/BookQuickReservationModal";

const QuickReservation = ({advertisement, quickReservation, showBookButton}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const closeModal = (e) => {
    setIsModalOpen(false);
  }

  return ( 
  <div className='flex text-left rounded-lg bg-white'>
    <div className="block w-full rounded-lg overflow-hidden border border-slate-200 shadow-sm">

      {/* red top */}
      <div className="flex flex-col justify-center h-6 w-full bg-red-700
      font-mono text-center text-white text-sm font-bold">
        Save {advertisement.currency}{quickReservation.calculatedOldPrice - quickReservation.newPrice}!
      </div>

      <div className="flex flex-col text-xl w-full text-slate-700
      tracking-tight my-auto text-left leading-5 p-2">
        <div className="block">

          <div className="flex justify-center text-sm rounded-md border border-slate-400">
            <p>{moment(quickReservation.startDateTime).format("DD-MM-yyyy HH:mm")}</p>
          </div>

          <Icon icon="tabler:chevron-down" inline={true} className="my-auto mx-auto text-center mt-1 text-slate-400"/>

          <div className="flex justify-center text-sm rounded-md border border-slate-400 mt-1">
            <p>{moment(quickReservation.endDateTime).format("DD-MM-yyyy HH:mm")}</p>
          </div>

          <div className="flex justify-center text-lg mt-1.5">
            <p className="flex">
              <Icon icon="tabler:users" inline={true} className="my-auto mr-1"/>
              {quickReservation.capacity}
            </p>
          </div>
        </div>

        <div className="flex justify-between mt-8">
          <h1 className="font-mono font-medium text-slate-700 text-xl underline mt-0.5">
            {advertisement.currency}{quickReservation.calculatedOldPrice}
          </h1>

          <h1 className="font-mono font-bold text-4xl text-red-600">
            {advertisement.currency}{quickReservation.newPrice}
          </h1>
        </div>

        { showBookButton &&
          <button className="w-full text-white bg-red-700 hover:bg-red-800 active:bg-red-900 rounded-md font-sans
          font-medium py-1 pb-1.5 mt-1" onClick={() => setIsModalOpen(true)}>
            Book
          </button>
        }
      </div>
    </div>

    {isModalOpen &&
      <BookQuickReservationModal
        quickReservation={quickReservation}
        advertisement={advertisement}
        close={closeModal}/>
    }
  </div>
  );
}
 
export default QuickReservation;