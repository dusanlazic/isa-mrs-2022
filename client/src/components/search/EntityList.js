import { useState, useEffect } from 'react';
import { Icon } from '@iconify/react';

import EntityCard from "./EntityCard";
import ReactPaginate from "react-paginate";

const EntityList = ({data, entityType, currentPage, setPage, totalPages, getSorting, refreshList }) => {

  const [sorting, setSorting] = useState('price');
  const [descending, setDescending] = useState(true);

  useEffect(() => {
    getSorting.current = sendSorting; 
  }, []);

  const sendSorting = () => {
    return [sorting, descending];
  }

  const updateSorting = (value) => {
    setSorting(value);
    refreshList(true);
  }

  const updateOrder = () => {
    setDescending(!descending);
    refreshList(true);
  }

  const handlePageClick = (event) => {
    setPage(event.selected);
  };

  return ( 
    <div className="w-full text-left">
      <div className="bg-white rounded-xl shadow-sm p-6">

        <div className="flex justify-end mb-4">
          <div className="flex justify-between gap-x-3">

            {/* choose order */}
            <div>
              <button onClick={() => updateOrder()} className='py-1'>
                {descending &&
                  <Icon className='my-auto mx-auto h-6 w-6 '
                  icon='tabler:sort-descending'/> 
                }
                {!descending &&
                  <Icon className='my-auto mx-auto h-6 w-6'
                  icon='tabler:sort-ascending'/>
                }
              </button>
            </div>

            {/* choose sorting */}
            <div className='flex justify-center h-8 my-auto'>
              <button onClick={() => updateSorting('price')}
              className={`w-20 py-1 rounded-l-lg text-sm font-bold
              ${sorting === 'price' ? 'text-slate-100 bg-slate-800' : 'bg-slate-100 text-slate-800\
              border-2 border-r-0 border-slate-800'}`}>
                PRICE
              </button>

              <button onClick={() => updateSorting('rating')}
              className={`w-20 py-1 rounded-r-lg text-sm font-bold
              ${sorting === 'rating' ? 'text-slate-100 bg-slate-800' : 'bg-slate-100 text-slate-800\
              border-2 border-l-0  border-slate-800'}`}>
                RATING
              </button>
            </div>

          </div>
        </div>
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