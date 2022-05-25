import EntityCard from "./EntityCard";
import ReactPaginate from "react-paginate";

const EntityList = ({data, entityType, currentPage,setPage, totalPages }) => {

  const handlePageClick = (event) => {
    setPage(event.selected);
  };

  return ( 
    <div className="w-full text-left">
      <div className="bg-white rounded-xl shadow-sm p-6">
        <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-x-4 gap-y-4">
          {data.map(entity => 
            <div key={entity.id}>
              <EntityCard entity={entity} entityType={entityType}/>
            </div>
          )}
        </div>
        <div className="mt-10 mb-4 mx-auto h-10 w-full"> 
          <ReactPaginate
          breakLabel="..."
          nextLabel=">"
          onPageChange={handlePageClick}
          pageRangeDisplayed={5}
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

          marginPagesDisplayed={2}
          containerClassName="flex justfy-center bg-slate-200 p-1 rounded-xl w-min mx-auto"

          activeClassName="bg-white text-green-600 border border-slate-400 rounded-lg"
        />
        </div>
      </div> 
    </div>
   );
}
 
export default EntityList;