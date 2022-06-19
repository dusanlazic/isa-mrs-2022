import moment from "moment";
import { Link } from 'react-router-dom'

const ReservationItem = ({ reservation, extend }) => {

  const getPlaceholderImage = () => {
    if (reservation.advertisement.advertisementType === 'resort') return '/images/property-placeholder.jpg'
    if (reservation.advertisement.advertisementType === 'boat') return '/images/boat-placeholder.jpg'
    if (reservation.advertisement.advertisementType === 'adventure') return '/images/fish-placeholder.jpg'
  }

  return (
    <div className="flex text-left bg-gray-50 p-4 rounded-md transition-colors">
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
            {reservation.calculatedPrice}€
          </h1>
        </div>
        <div className="flex justify-between">

          <div className="block">
            <div className="text-sm text-gray-500">
              <b>From:</b> {moment(reservation.startDateTime, "DD/MM/yyyy hh:mm").format("DD.MM.yyyy")}
            </div>
            <div className="text-sm text-gray-500">
              <b>To:</b> {moment(reservation.endDateTime, "DD/MM/yyyy hh:mm").format("DD.MM.yyyy.")}
            </div>
            <div className="text-sm text-gray-500">
              <b>By:</b> {reservation.customer.firstName} {reservation.customer.lastName}
            </div>
          </div>

          <div className="flex mt-auto divide-x h-min">
            {reservation.canBeReportedOn &&
              <div>
                <button className={`hover:text-cyan-600 pl-1.5 ${reservation.canBeExtended ? 'pr-1.5' : ''}`}>
                  Report
                </button>
              </div>}
            {reservation.canBeExtended &&
              <div >
                <button className="hover:text-cyan-600 pl-1.5" onClick={() => extend(reservation)}>
                  Extend
                </button>
              </div>}
          </div>
        </div>
      </div>
    </div>

  );
}

export default ReservationItem;