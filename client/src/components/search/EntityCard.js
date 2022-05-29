import { Link } from "react-router-dom";
import { Icon } from "@iconify/react";

const EntityCard = ({entity, entityType}) => {

  const getPlaceholderImage = (what) => {
    if (what === 'resorts') return '/images/property-placeholder.jpg'
    if (what === 'boats') return '/images/boat-placeholder.jpg'
    if (what === 'adventures') return '/images/fish-placeholder.jpg'
  }

  const getEntityLink = () => {
    return `/${entityType.substring(0, entityType.length - 1)}/${entity.id}`;
  }

  return ( 
      <div className="w-full mx-auto">
        <Link to={getEntityLink()}>
          <img src={entity.photo ? `/api/${entity.photo}` : getPlaceholderImage(entityType)} alt=""
          className="h-32 sm:h-40 w-full mx-auto object-cover rounded-lg"/>
        </Link>

        <div className="flex text-xs mt-2">
          <Icon className='text-green-700 my-auto' icon="tabler:star" inline={true} />
          <div className="ml-1 pt-0.5">4.75 (254)</div>
        </div>

        <div className="text-sm font-medium text-raisin-black">
          <Link to={getEntityLink()}>
            {entity.title}
          </Link>
        </div>

        <div className="text-xs -mt-1 text-slate-400">
          {entity.address.city}, {entity.address.countryCode}
        </div>

        <div className="text-sm font-medium mt-1 text-green-700">
        {entity.currency} {entityType === 'adventures' ? entity.pricePerPerson + '/person' : entity.pricePerDay + '/day'}
        </div>
        
        {/* <div className="text-xs text-slate-500 mt-0.5">
          {entity.description.length > 50 ? entity.description.substring(0, 50) + '...' : entity.description}
        </div> */}
      </div>
   );
}
 
export default EntityCard;