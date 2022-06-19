import { useState, useEffect } from 'react';
import { get } from '../../../adapters/xhr';

import ClientSubscriptionCard from './ClientSubscriptionCard'

let dataReceived = false;

const ClientSubscriptionList = ({client}) => {
  const [subscriptions, setSubscriptions] = useState([]);

  const removeSubscription = (id) => {
    setSubscriptions(subscriptions.filter(x => x.id !== id));
  }

	useEffect(() => {
    dataReceived = false;
		get(`/api/customers/subscriptions`)
    .then(response => {
			setSubscriptions(response.data);
      dataReceived = true;
		  });
		}, [])

  return ( 
    <div>
      {!dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">Loading...</h1>
      }
      {subscriptions.length === 0 && dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">You haven't subscribed to any entities.</h1>
      }
      {subscriptions.length > 0 &&
      <div className='grid grid-cols-2 sm:grid-cols-4 gap-x-4 gap-y-4'>
        {subscriptions.map(advertisement =>
          <ClientSubscriptionCard key={advertisement.id} data={advertisement} removeSubscription={removeSubscription}/>
        )}
      </div>}
    </div>
   );
}
 
export default ClientSubscriptionList;