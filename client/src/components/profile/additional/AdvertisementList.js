import { useState, useEffect } from 'react';
import { get } from '../../../adapters/xhr';

import AdvertisementCard from './AdvertisementCard'

let dataReceived = false;
const AdvertisementList = ({advertiserId}) => {
  const [advertisements, setAdvertisements] = useState([]);

	useEffect(() => {
    dataReceived = false;
		get(`/api/advertisers/${advertiserId}/advertisements`)
    .then(response => {
			setAdvertisements(response.data);
      dataReceived = true;
		  });
		}, [])

  return ( 
    <div>
      {!dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">Loading...</h1>
      }
      {advertisements.length === 0 && dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">Advertiser has not posted any ads.</h1>
      }
      {advertisements.length > 0 &&
      <div className='grid grid-cols-2 sm:grid-cols-3 gap-x-4 gap-y-4'>
        {advertisements.map(advertisement =>
          <AdvertisementCard key={advertisement.id} data={advertisement}/>
        )}
      </div>}
    </div>
   );
}
 
export default AdvertisementList;