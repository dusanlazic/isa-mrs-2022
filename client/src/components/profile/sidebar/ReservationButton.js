import { useEffect } from "react";
import { useState } from "react";
import { get, patch } from "../../../adapters/xhr";
import { getSession } from "../../../contexts";
import CustomerReservationModal from "../../modals/reservation/CustomerReservationModal";

const ReservationButton = ({data}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [canSubscribe, setCanSubscribe] = useState(false);
  const [subscribed, setSubscribed] = useState(false);
  const [subscribeButtonText, setSubscribeButtonText] = useState('Subscribe for discounts');

  useEffect(() => {
    const session = getSession();
    if (session !== null && session.accountType === 'CUSTOMER') {
      get('/api/customers/subscriptions')
        .then(response => {
          const subscriptions = response.data;
          setCanSubscribe(true);
          if (subscriptions.filter(s => s.id === data.id).length > 0) {
            setSubscribed(true);
            setSubscribeButtonText('Subscribed');
          }
      })
    }
  }, [subscribed])

  const handleSubscribe = () => {
    let endpoint = 'subscribe';
    if (subscribed) endpoint = 'unsubscribe';
    patch(`/api/ads/${data.id}/${endpoint}`)
      .then(response => {
        if (endpoint === 'subscribe') {
          setSubscribed(true);
          setSubscribeButtonText('Subscribed');
        } else {
          setSubscribed(false);
          setSubscribeButtonText('Subscribe for discounts');
        }
      })
      .catch(error => {
        
      })
  }

  return ( 
    <div className="border border-gray-100 rounded-lg text-left
      shadow-sm py-3 px-5 tracking-wide">
        <button className="rounded-xl shadow-sm px-6 py-2 text-white font-bold 
        bg-cyan-700 hover:bg-cyan-800 active:bg-cyan-900 focus:outline-none
        w-full" onClick={() => setIsModalOpen(true)}>
          Make a reservation
        </button>

        { canSubscribe &&
          <button className="rounded-xl shadow-sm px-6 py-2 text-white font-bold mt-2 
          bg-red-800 hover:bg-red-900 active:bg-red-900 focus:outline-none
          w-full" onClick={() => handleSubscribe()}
          onMouseEnter={() => {subscribed ? setSubscribeButtonText('Unsubscribe') : setSubscribeButtonText('Subscribe for discounts')}}
          onMouseLeave={() => {subscribed ? setSubscribeButtonText('Subscribed') : setSubscribeButtonText('Subscribe for discounts')}}>
            { subscribeButtonText }
          </button>
        }

        {isModalOpen &&
          <CustomerReservationModal data={data} close={() => setIsModalOpen(false)}/>
        }
      </div>
   );
}
 
export default ReservationButton;