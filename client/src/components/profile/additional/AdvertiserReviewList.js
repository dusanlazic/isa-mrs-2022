import { useState, useEffect } from 'react';
import { get } from '../../../adapters/xhr';

import AdvertiserReview from './AdvertiserReview'

let dataReceived = false;
const AdvertiserReviewList = ({advertiserId}) => {
  const [reviews, setReviews] = useState([]);

	useEffect(() => {
		get(`/api/advertisers/${advertiserId}/reviews`)
    .then(response => {
      console.log(response.data)
			setReviews(response.data);
      dataReceived = true;
		  });
		}, [])

  return ( 
    <div>
      {!dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">Loading...</h1>
      }
      {reviews.length === 0 && dataReceived &&
        <h1 className="text-xl font-medium text-gray-900">Advertiser has not received any reviews yet.</h1>
      }
      {reviews.length > 0 &&
      <div className='flex flex-col gap-y-4'>
        {reviews.map(advertisement =>
          <AdvertiserReview key={advertisement.id} data={advertisement}/>
        )}
      </div>}
    </div>
   );
}
 
export default AdvertiserReviewList;