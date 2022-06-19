import { useState, useEffect } from "react";
import { get, patch } from "../adapters/xhr";
import { Icon } from '@iconify/react';
import Moment from 'moment';

const ReservationReportsPage = () => {
  const [reports, setReports] = useState(null);
  
  useEffect(() => {
    get(`/api/admin/reports/`)
    .then((response) => {
      setReports(response.data);
    });
  }, [])

  const approveReport = (index, id) => {
    patch(`/api/admin/reports/${id}`, { approve: true });

    let newReports = [...reports];
    newReports.splice(index, 1)
    setReports(newReports)
  }

  const rejectReport = (index, id) => {
    patch(`/api/admin/reports/${id}`, { approve: false });

    let newReports = [...reports];
    newReports.splice(index, 1)
    setReports(newReports)
  }

  if (reports == null)
    return null

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">
      <h1 className="text-2xl text-left text-gray-400 mb-6 font-sans">
        Pending reservation reports:
        <span className="text-gray-800"> {reports.length}</span>
      </h1>

      <div className="flex flex-col divide-y divide-dashed h-full border-0.5 rounded-lg gap-y-8">
        {reports.map((report, index) => 
        <div key={index} className="block rounded-lg">
          <div className="grid grid-cols-10 pt-3">
            <div className="block col-span-7">
              <h1 className="text-xl text-gray-700 font-bold tracking-tight my-auto text-left">
                  {report.reservation.customer.firstName} {report.reservation.customer.lastName}
                  <span className="font-normal">
                    &nbsp;for advertisement&nbsp;
                  </span>
                  {report.reservation.advertisement.title}
              </h1>
              <div className="text-left mt-4">
              {report.comment}
              </div>
              <div className="flex mt-4 text-gray-600 text-sm">
                <Icon className="mr-2" icon="tabler:clock" inline={true} width="20" /> 
                <span className="mr-4">{Moment(report.createdAt).format('D MMM YYYY HH:mm')}</span>
                <Icon className="mr-2" icon="tabler:hammer" inline={true} width="20" /> 
                <span className="mr-4">Penalty requested</span>
                {report.customerWasLate &&
                <>
                  <Icon className="mr-2" icon="tabler:hourglass-low" inline={true} width="20" /> 
                  <span className="mr-4">Was late</span>
                </>
                }
              </div>
            </div>
            <div className="block col-span-3">
              <button className="bg-gray-300 hover:bg-gray-400 active:bg-gray-500 drop-shadow-md
              text-gray-700 rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 mb-1.5 mt-3 md:mt-0"
              onClick={() => rejectReport(index, report.id)}>
                Reject
              </button>
              <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base w-1/3 ml-2 mb-1.5 mt-3 md:mt-0"
              onClick={() => approveReport(index, report.id)}>
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
 
export default ReservationReportsPage;