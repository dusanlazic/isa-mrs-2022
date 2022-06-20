import { useState } from "react";
import { post } from "../../../adapters/xhr";
import { Icon } from "@iconify/react";
import MessageModal from "../MessageModal";
import moment from "moment";

const BookQuickReservationModal = ({quickReservation, advertisement, close, removeQuickReservation}) => {
  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');

  const [isReservationSuccessful, setIsReservationSuccessful] = useState(false);

  const bookQuickReservation = () => {
    post(`/api/reservations/${quickReservation.id}/book-quick-reservation`)
    .then(response => {
      setIsReservationSuccessful(true);
      setMessageModalText('Successfully booked reservation!');
      setShowMessageModal(true);
    })
    .catch(error => {
      setIsReservationSuccessful(false);
      setMessageModalText(error.response.data.message);
      setShowMessageModal(true);
    })
  }
  const closeMessageModal = (e) => {
    if (e.target === e.currentTarget) {
      close();
    }
  }

  return ( 
    <div onClick={closeMessageModal} className="fixed top-0 left-0 z-40 w-full min-h-screen h-screen text-center
    flex items-center justify-center bg-gray-900 bg-opacity-70 font-mono transition-opacity text-base">
      <div className="relative flex flex-col w-96 h-150 bg-white rounded-xl mx-auto overflow-hidden p-9">
        <h1 className="text-xl mb-4 font-display">Quick reservation</h1>

        <label className="text-xs text-left text-slate-500">When:</label>
        <div className="flex justify-between border-b">
          <div className="flex justify-center text-sm rounded-md">
            <p>{moment(quickReservation.startDateTime).format("DD-MM-yyyy HH:mm")}</p>
          </div>

          <Icon icon="tabler:chevron-right" inline={true} className="my-auto mx-auto text-center text-slate-400"/>

          <div className="flex justify-center text-sm rounded-md">
            <p>{moment(quickReservation.endDateTime).format("DD-MM-yyyy HH:mm")}</p>
          </div>
        </div>

        <div className="flex justify-between border-b mt-3">
          <label className="text-xs text-left text-slate-500">Max attendees:</label>
          <div className="flex justify-center text-sm rounded-md">
            <p>{quickReservation.capacity}</p>
          </div>
        </div>

        { quickReservation.selectedOptions.length > 0 &&
          <div className="block mt-3 max-h-48 overflow-auto">
          <p className="text-xs text-left text-slate-500 -mb-1.5">Selected options:</p>
          {quickReservation.selectedOptions.map(selectedOption => 
            <div key={selectedOption.id} className="flex justify-between border-b mt-2 text-slate-700 text-left">
              
              <div className="flex justify-center text-sm rounded-md">
                {selectedOption.option.name}
              </div>

              <div className="flex flex-col justify-end text-sm rounded-md">
                { selectedOption.option.maxCount > 1 ? selectedOption.count :
                  <Icon icon="tabler:check" className="mb-0.5 mx-auto text-center"/>
                }
              </div>

            </div>
            )}
        </div>
        }

        <div className="flex justify-between border-b mt-8">
          <label className="text-xs text-left text-slate-500">Final price:</label>
          <div className="flex justify-center text-sm rounded-md">
            <p>{advertisement.currency}{quickReservation.newPrice}</p>
          </div>
        </div>
        
        <div>
          <button className="rounded-xl shadow-sm px-6 py-2 text-white font-bold 
          bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
          w-full mt-4" onClick={bookQuickReservation}>
            Book
          </button>
        </div>

        {showMessageModal &&
        <MessageModal okayFunction={(e) => {removeQuickReservation(quickReservation.id); close(e);}}
        closeFunction = {() => setShowMessageModal(false)}
        text = { messageModalText }
        deactivateOkayFunction = { !isReservationSuccessful }
        />}
        
      </div>
    </div>
   );
}
 
export default BookQuickReservationModal;