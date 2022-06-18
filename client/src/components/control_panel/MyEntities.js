import AdvertisementCard from "../profile/additional/AdvertisementCard";
import ReactPaginate from "react-paginate";
import { useEffect, useState } from "react"
import { get } from "../../adapters/xhr";

const MyEntities = () => {

  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);

  const [sorting, setSorting] = useState('title');
  const [advertisements, setAdvertisements] = useState([]);
  const [descending, setDescending] = useState(true);



    useEffect(() => {
      setAdvertisements(null);
    }, [])
  
    useEffect(() => {
      fetchData();
    }, [currentPage])
  
    const fetchData = (resetPage = false) => {
      setCurrentPage(resetPage ? 0 : currentPage);
      get(`/api/advertisers/advertisements?page=${currentPage}&sorting=${sorting}`).then((response) => {
        setAdvertisements(response.data.content);
        setTotalPages(response.data.totalPages);
      });
    }
  
    useEffect(() => {
      fetchData(true);
    }, [sorting, descending]);
  
    const handlePageClick = (event) => {
      setCurrentPage(event.selected);
    };
  
    if (advertisements === null) {
      return null;
    }

  return ( 
    <div>
    {advertisements.length === 0 &&
      <div className="flex flex-col">
        <div>You don't own any advertisements.</div>
      </div>
    }
      {advertisements.length > 0 &&
        <div className="grid grid-cols-2 lg:grid-cols-3 gap-4 mx-auto justify-items-center">
          {advertisements != null && advertisements.map(advertisement =>
            <div key={advertisement.id} className="w-full">
            <AdvertisementCard key={advertisement.id} data={advertisement}/>
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
export default MyEntities;