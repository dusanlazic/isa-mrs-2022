import { useState, useEffect } from "react";
import { get } from '../adapters/xhr';
import { useLocation } from "react-router-dom";
import EntityList from "../components/search/EntityList";

import SearchAndFilter from "../components/search/SearchAndFilter";

const SearchPage = () => {
  const location = useLocation();
  const urlList = location.pathname.split('/');
  const entity = urlList[urlList.length-1];

  const [entityList, setEntityList] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    setEntityList([]);
  }, [location])

  useEffect(() => {
    get(`/api/ads/${entity}/search?page=${currentPage}`)
    .then(response => {
      console.log(response.data)
      setEntityList(response.data.content);
      setTotalPages(response.data.totalPages);
      setTotalElements(response.data.totalElements);
    })
  }, [location, currentPage])

  const where = new URLSearchParams(location.search).get("where");
  const startDate = new URLSearchParams(location.search).get("startDate");
  const endDate = new URLSearchParams(location.search).get("endDate");
  const guests = new URLSearchParams(location.search).get("guests");

  return ( 
    <div key={location.key} className="py-20 px-2 sm:px-14 md:px-20 lg:px-40 xl:px-64 bg-silver-accent min-h-screen">
      <SearchAndFilter whereProp={where ? where : ''} guestsProp={guests ? guests : ''}
      entityProp={entity} startDateProp={startDate} endDateProp={endDate} totalElements={totalElements}/>
      {entityList.length > 0 &&
      <div className="mt-6">
        <EntityList data={entityList} entityType={entity} currentPage={currentPage} setPage={setCurrentPage} totalPages={totalPages}/>
      </div>}
    </div>
   );
}
 
export default SearchPage;