import moment from "moment";
import { Link } from 'react-router-dom'

const ClientReservationItem = ({reservation, allowCancel, cancel, review, report}) => {

  const getPlaceholderImage = () => {
    if (reservation.advertisement.advertisementType === 'resort') return '/images/property-placeholder.jpg'
    if (reservation.advertisement.advertisementType === 'boat') return '/images/boat-placeholder.jpg'
    if (reservation.advertisement.advertisementType === 'adventure') return '/images/fish-placeholder.jpg'
  }

  return ( 
  <div className={`flex text-left rounded-lg p-1.5 px-2
  ${reservation.cancelled ? 'bg-red-100' : 'bg-white'}`}>
    <Link to={`/${reservation.advertisement.advertisementType}/${reservation.advertisement.id}`}>
      <img src={reservation.advertisement.photo ? `/api${reservation.advertisement.photo.uri}` : getPlaceholderImage()}
      alt="" className="flex-none h-14 w-20 object-cover rounded-lg" />
    </Link>
    <div className="block ml-2 w-full">
      <div className="flex justify-between text-xl w-full text-gray-700 tracking-tight my-auto text-left leading-5">
        <Link to={`/${reservation.advertisement.advertisementType}/${reservation.advertisement.id}`}>
          <h1>
            {reservation.advertisement.title}
          </h1>
        </Link>
        <h1 className="font-mono font-medium text-gray-700">
          {reservation.advertisement.currency}{reservation.calculatedPrice}
        </h1>
      </div>

      <div className="flex justify-between">

        <div className="block">
          <div className="text-sm text-gray-500">
            From: {moment(reservation.startDateTime).format("DD-MM-yyyy")}
          </div>
          <div className="text-sm text-gray-500">
            To: {moment(reservation.endDateTime).format("DD-MM-yyyy")}
          </div>
        </div>
        { allowCancel &&
        <div className="flex">
          <button className="hover:text-red-600" onClick={() => cancel(reservation)}>
            Cancel
          </button>
        </div>}

        <div className="flex my-auto divide-x h-min">
          { !allowCancel && reservation.canBeReviewed &&
          <div>
            <button className={`hover:text-cyan-600 
            ${reservation.canBeComplainedAbout ? 'pr-1.5' : ''}`} onClick={() => review(reservation)}>
              Review
            </button>
          </div>}
          { !allowCancel && reservation.canBeComplainedAbout &&
          <div >
            <button className="hover:text-red-600 pl-1.5" onClick={() => report(reservation)}>
              Report
            </button>
          </div>}
        </div>

      </div>
    </div>
  </div>
  );
}
 
export default ClientReservationItem;