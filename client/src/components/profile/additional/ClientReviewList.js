import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import ClientReview from './ClientReview'

const ClientReviewList = ({data}) => {

	const [reviews, setReviews] = useState(null);

	useEffect(() => {
		get(`/api/customers/${data.id}/reviews`).then((response) => {
			setReviews(response.data);
		  });
		}, [])

    
  if (reviews === null) {
    return null;
  }

  return ( 
    <div>
      {reviews.length === 0 && 
        <div className="flex flex-col gap-y-6">
          <div>You haven't left any reviews.</div>
        </div>
      } 
      {reviews.length > 0 && 
        <div className="flex flex-col gap-y-6">
        {reviews.map((review) => 
          <ClientReview review={review} key={review.id}/>
        )}
      </div>
      }
    </div>
   );
}
 
export default ClientReviewList;