import { useEffect, useState } from "react"
import { useParams, useNavigate } from 'react-router-dom'
import { get } from "../../adapters/xhr";
import ReservationItem from "./ReservationItem";
import ReactPaginate from "react-paginate";

const ReservationHistory = () => {

  const [reservations, setReservations] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);

  const [sorting, setSorting] = useState('startDateTime');
  const [descending, setDescending] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    setReservations(null);
  }, [])

  useEffect(() => {
    fetchData();
  }, [currentPage])

  const fetchData = (resetPage = false) => {
    setCurrentPage(resetPage ? 0 : currentPage);
    get(`/api/advertisers/reservations?page=${currentPage}&sorting=${sorting}`).then((response) => {
      setReservations(response.data.content);
      setTotalPages(response.data.totalPages);
    });
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
      {reservations.length === 0 &&
        <div className="flex flex-col">
          <div>You don't have any reservations.</div>
        </div>
      }
      {reservations.length > 0 &&
        <div className="grid 2xl:grid-cols-2 gap-4 mx-auto justify-items-center">
          {reservations != null && reservations.map(reservation =>
            <div key={reservation.id} className="w-full">
              <ReservationItem reservation={reservation} />
            </div>
          )}

        </div>
      }
      <div className="mt-10 mb-4 h-10 w-full font-sans">
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
  );
}

export default ReservationHistory;