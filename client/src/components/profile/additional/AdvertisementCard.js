import { Icon } from "@iconify/react";
import { Link } from "react-router-dom";

const AdvertisementCard = ({data}) => {

  const getPlaceholderImage = () => {
    if (data.advertisementType === 'resort') return '/images/property-placeholder.jpg'
    if (data.advertisementType === 'boat') return '/images/boat-placeholder.jpg'
    if (data.advertisementType === 'adventure') return '/images/fish-placeholder.jpg'
  }
  
  return ( 
    <div className="w-full mx-auto text-left">
        <Link to={`/${data.advertisementType}/${data.id}`}>
          <img src={data.photo ? `/api/${data.photo}` : getPlaceholderImage()} alt=""
          className="h-32 sm:h-40 w-full mx-auto object-cover rounded-lg"/>
        </Link>

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
 
export default AdvertisementCard;