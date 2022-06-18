import { useEffect, useState } from "react"
import { get } from "../../../adapters/xhr";
import ClientReservationItem from "./ClientReservationItem";
import CustomerReviewModal from "../../modals/review/CustomerReviewModal";
import CustomerComplaintModal from "../../modals/complaint/CustomerComplaintModal";
import ReactPaginate from "react-paginate";

const ClientReservationHistory = ({data}) => {
	const [reservations, setReservations] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);

  const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);
  const [isComplaintModalOpen, setIsComplaintModalOpen] = useState(false);
  const [reservationToReview, setReservationToReview] = useState(null);
  const [reservationToReport, setReservationToReport] = useState(null);

  const [sorting, setSorting] = useState('startDateTime');
  const [descending, setDescending] = useState(true);

  useEffect(() => {
    setReservations(null);
  }, [])

  useEffect(() => {
    fetchData();
  }, [currentPage])
  
  const fetchData = (resetPage=false) => {
    setCurrentPage(resetPage ? 0 : currentPage);
		get(`/api/customers/previous-reservations?page=${currentPage}&sorting=${sorting}`).then((response) => {
			setReservations(response.data.content);
      setTotalPages(response.data.totalPages);
    });
  }

  const initReview = reservation => {
    setReservationToReview(reservation);
    setIsReviewModalOpen(true);
  } 

  const initReport = reservation => {
    setReservationToReport(reservation);
    setIsComplaintModalOpen(true);
  } 

  useEffect(() => {
    fetchData(true);
  }, [sorting, descending]);

  const handlePageClick = (event) => {
    setCurrentPage(event.selected);
  };

  if (reservations === null) {
    return null;
  }

  return ( 
    <div>
      { reservations.length === 0 &&
        <div className="flex flex-col">
          <div>You haven't made any reservations.</div>
        </div>
      }
      { reservations.length > 0 &&
        <div className="flex flex-col gap-y-0.5">
          {reservations.map(reservation => 
            <div key={reservation.id}>
              <ClientReservationItem reservation={reservation}
              allowCancel={false} review={initReview} report={initReport}/>
            </div>
          )}
          <div className="mt-10 mb-4 mx-auto h-10 w-full font-sans"> 
            <ReactPaginate
              breakLabel="..."
              nextLabel=">"
              onPageChange={handlePageClick}
              pageRangeDisplayed={2}
              pageCount={totalPages}
              forcePage={currentPage}
              previousLabel="<"
              
              pageClassName="text-gray-700 font-medium w-10 h-10"
              pageLinkClassName="flex flex-col text-center justify-center w-full h-full "

              previousClassName="w-10 h-10 text-2xl select-none text-slate-600 hover:text-black"
              previousLinkClassName="flex justify-center w-full h-full"

              nextClassName="w-10 h-10 text-2xl select-none text-slate-600 hover:text-black"
              nextLinkClassName="flex justify-center w-full h-full"

              breakClassName=""
              breakLinkClassName=""

              disabledClassName="!text-gray-400 hover:text-gray-400"
              disabledLinkClassName=""

              marginPagesDisplayed={1}
              containerClassName="flex justfy-center bg-slate-200 p-1 rounded-xl w-min mx-auto"

              activeClassName="bg-white text-green-600 border border-slate-400 rounded-lg"
            />
          </div>
        </div>
      }
    
      { isReviewModalOpen &&
        <CustomerReviewModal data={reservationToReview} 
        close={() => setIsReviewModalOpen(false)}/>
      }

      { isComplaintModalOpen &&
        <CustomerComplaintModal data={reservationToReport} 
        close={() => setIsComplaintModalOpen(false)}/>
      }

    </div>
   );
}
 
export default ClientReservationHistory;