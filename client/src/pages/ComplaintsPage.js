import { useState, useEffect } from "react";
import { get, patch } from "../adapters/xhr";
import { Icon } from '@iconify/react';
import RespondToComplaintModal from "../components/modals/complaint/RespondToComplaintModal";
import Moment from 'moment';

const ReservationComplaintsPage = () => {
  const [complaints, setComplaints] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [complaintToRespond, setComplaintToRespond] = useState(null);
  
  const [complaintIndex, setComplaintIndex] = useState(null);

  useEffect(() => {
    get(`/api/admin/complaints/`)
    .then((response) => {
      setComplaints(response.data);
    });
  }, [])

  const respondToComplaint = (index) => {
    setComplaintToRespond(complaints[index]);
    setComplaintIndex(index);
    setIsModalOpen(true);
  }

  const removeComplaintFromList = () => {
    let newComplaints = [...complaints];
    newComplaints.splice(complaintIndex, 1)
    setComplaints(newComplaints)
  }

  const ignoreComplaint = (index, id) => {
    let newComplaints = [...complaints];
    newComplaints.splice(index, 1)
    setComplaints(newComplaints)
  }

  if (complaints == null)
    return null

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">
      <h1 className="text-2xl text-left text-gray-400 mb-6 font-sans">
        Pending complaints:
        <span className="text-gray-800"> {complaints.length}</span>
      </h1>

      <div className="flex flex-col divide-y divide-dashed h-full border-0.5 rounded-lg gap-y-8">
        {complaints.map((complaint, index) => 
        <div key={index} className="block rounded-lg">
          <div className="grid grid-cols-10 pt-3">
            <div className="block col-span-7">
              <h1 className="text-xl text-gray-700 font-bold tracking-tight my-auto text-left">
                  {complaint.customer.firstName} {complaint.customer.lastName}
                  <span className="font-normal">
                    &nbsp;for ad&nbsp;
                  </span>
                  {complaint.advertisement.title}
                  <span className="font-normal">
                    &nbsp;by&nbsp;
                  </span>
                  {complaint.advertisement.advertiser.firstName} {complaint.advertisement.advertiser.lastName}
              </h1>
              <div className="text-left mt-4">
              {complaint.comment}
              </div>
              <div className="flex mt-4 text-gray-600 text-sm">
                <Icon className="mr-2" icon="tabler:clock" inline={true} width="20" /> 
                <span className="mr-4">{Moment(complaint.createdAt).format('D MMM YYYY HH:mm')}</span>
              </div>
            </div>
            <div className="block col-span-3">
              <button className="bg-gray-300 hover:bg-gray-400 active:bg-gray-500 drop-shadow-md
              text-gray-700 rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 mb-1.5 mt-3 md:mt-0"
              onClick={() => ignoreComplaint(index, complaint.id)}>
                Ignore
              </button>
              <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 ml-2 mb-1.5 mt-3 md:mt-0"
              onClick={() => respondToComplaint(index, complaint.id)}>
                Respond
              </button>
            </div>
          </div>
        </div>
        )}
      </div>

      { isModalOpen &&
        <RespondToComplaintModal data={complaintToRespond} 
        close={() => setIsModalOpen(false)}
        success={() => { removeComplaintFromList(); }}/>
      }

    </div>
   );
}
 
export default ReservationComplaintsPage;