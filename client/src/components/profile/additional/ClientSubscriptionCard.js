import { Icon } from "@iconify/react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { patch } from "../../../adapters/xhr";

const ClientSubscriptionCard = ({data, removeSubscription}) => {
  const [showUnsubscribe, setShowUnsubscribe] = useState(false);

  const getPlaceholderImage = () => {
    if (data.advertisementType === 'resort') return '/images/property-placeholder.jpg'
    if (data.advertisementType === 'boat') return '/images/boat-placeholder.jpg'
    if (data.advertisementType === 'adventure') return '/images/fish-placeholder.jpg'
  }

  const unsubscribe = () => {
    patch(`/api/ads/${data.id}/unsubscribe`)
      .then(response => {
        removeSubscription(data.id);
      })
      .catch(error => {
        
      })
  }
  
  return ( 
    <div className="w-full mx-auto text-left">
        <div className="relative" onMouseEnter={() => setShowUnsubscribe(true)}
        onMouseLeave={() => {setShowUnsubscribe(false)}}>
          <div className={`absolute justify-center z-20 bg-raisin-black
          bg-opacity-50 w-full h-full rounded-lg text-center font-bold text-white
          ${showUnsubscribe ? 'flex flex-col cursor-pointer' : 'hidden'}`}
          onClick={() => unsubscribe()}>
            Unsubscribe
          </div>
          <img src={data.photo ? `/api/${data.photo.uri}` : getPlaceholderImage()} alt=""
          className="h-32 sm:h-28 lg:h-24 xl:h-32 w-full mx-auto object-cover rounded-lg"/>
        </div>

        <div className="flex text-xs mt-2">
          <Icon className='text-green-700 my-auto' icon="tabler:star" inline={true} />
          <div className="ml-1 pt-0.5">4.75 (254)</div>
        </div>

        <div className="text-sm font-medium text-raisin-black">
          <Link to={`/${data.advertisementType}/${data.id}`}>
            {data.title}
          </Link>
        </div>

        <div className="text-xs -mt-1 text-slate-400">
          {data.address.city}, {data.address.countryCode}
        </div>

      </div>
   );
}
 
export default ClientSubscriptionCard;