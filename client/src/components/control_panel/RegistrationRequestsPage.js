import { useState, useEffect } from "react";
import { get, patch } from "../../adapters/xhr";
import { Icon } from '@iconify/react';
import Moment from 'moment';

const RegistrationRequestsPage = () => {
  const [requests, setRequests] = useState(null);
  
  const accountTypeIconMap = new Map();
  accountTypeIconMap.set('RESORT_OWNER', { icon: 'tabler:home-2', name: "Resort owner" });
  accountTypeIconMap.set('BOAT_OWNER', { icon: 'tabler:speedboat', name: "Boat owner" });
  accountTypeIconMap.set('FISHING_INSTRUCTOR', { icon: 'tabler:fish', name: "Fishing instructor" });

  useEffect(() => {
    get(`/api/admin/registration-requests/`)
    .then((response) => {
      setRequests(response.data);
    });
  }, [])

  const approveRequest = (index, id) => {
    patch(`/api/admin/registration-requests/${id}`, { approve: true });

    let newRequests = [...requests];
    newRequests.splice(index, 1)
    setRequests(newRequests)
  }

  const rejectRequest = (index, id) => {
    let reason = prompt("Enter the reason for rejection:")
    
    if (reason === null)
      return

    patch(`/api/admin/registration-requests/${id}`, { approve: false, rejectionReason: reason });

    let newRequests = [...requests];
    newRequests.splice(index, 1)
    setRequests(newRequests)
  }

  if (requests == null)
    return null

  return ( 
    <div>
      <h1 className="text-2xl text-left text-gray-400 mb-6 font-sans">
        Pending registration requests:
        <span className="text-gray-800"> {requests.length}</span>
      </h1>

      <div className="flex flex-col divide-y divide-dashed h-full border-0.5 rounded-lg gap-y-8">
        {requests.map((request, index) => 
        <div key={index} className="block rounded-lg">
          <div className="grid grid-cols-10 pt-3">
            <div className="block col-span-7">
              <h1 className="text-xl text-gray-700 font-bold tracking-tight my-auto text-left">
                  {request.firstName} {request.lastName}
              </h1>
              <div className="flex mt-2 text-gray-600 text-sm">
                <Icon className="mr-2" icon={accountTypeIconMap.get(request.accountType).icon} inline={true} width="20" /> 
                  <span className="mr-4">{accountTypeIconMap.get(request.accountType).name}</span>
                <Icon className="mr-2" icon="tabler:clock" inline={true} width="20" /> 
                  <span className="mr-4">{Moment(request.createdAt).format('D MMM YYYY HH:mm')}</span>
              </div>
              <div className="text-left mt-4">
              {request.explanation}
              </div>
              <div className="flex mt-4 text-gray-600 text-sm">
                <Icon className="mr-2" icon="tabler:mail" inline={true} width="20" /> 
                  <span className="mr-4">{request.username}</span>
                <Icon className="mr-2" icon="tabler:phone" inline={true} width="20" /> 
                  <span className="mr-4">{request.phoneNumber}</span>
                <Icon className="mr-2" icon="tabler:building" inline={true} width="20" /> 
                  <span className="mr-4">{request.address}, {request.city}, {request.country}</span>
              </div>
            </div>
            <div className="flex col-span-3">
              <button className="bg-gray-300 hover:bg-gray-400 active:bg-gray-500 drop-shadow-md
              text-gray-700 rounded-lg py-2.5 lg:py-2 text-sm lg:text-base h-min w-20 mb-1.5 mt-3 md:mt-0"
              onClick={() => rejectRequest(index, request.id)}>
                Reject
              </button>
              <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base h-min w-20 ml-2 mb-1.5 mt-3 md:mt-0"
              onClick={() => approveRequest(index, request.id)}>
                Approve
              </button>
            </div>
          </div>
        </div>
        )}
      </div>
    </div>
   );
}
 
export default RegistrationRequestsPage;