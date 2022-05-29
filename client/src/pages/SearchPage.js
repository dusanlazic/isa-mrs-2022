import { useState, useEffect, useRef } from "react";
import { get } from '../adapters/xhr';
import { useLocation } from "react-router-dom";
import moment from 'moment';

import SearchAndFilter from "../components/search/SearchAndFilter";
import EntityList from "../components/search/EntityList";



const SearchPage = () => {
  const location = useLocation();
  const urlList = location.pathname.split('/');
  const entity = urlList[urlList.length-1];

  const [entityList, setEntityList] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);

  const getParams = useRef(null);

  useEffect(() => {
    setEntityList([]);
  }, [location])

  useEffect(() => {
    const [where, startDate, endDate, guests] = getParams.current();
    const query = getQuery(where, startDate, endDate, guests);
    get(`/api/ads/${entity}/search?page=${currentPage}${query}`)
    .then(response => {
      setEntityList(response.data.content);
      setTotalPages(response.data.totalPages);
      setTotalElements(response.data.totalElements);
    })
  }, [location, currentPage])

  const getQuery = (where, startDate, endDate, guests) => {
    let query = '';
    if (where.trim() !== '' || guests.trim() !== '' || startDate || endDate) {
      query += '&';
      query += where.trim() !== '' ? 'where=' + where.trim() : '';
      query += guests.trim() !== '' ? `${query !== '?' ? '&' : ''}guests=` + guests.trim() : '';
      
      if (startDate) {
        const start = moment(startDate).format('yyyy-MM-DD') + 'T00:00';
        query += start ? `${query !== '?' ? '&' : ''}startDate=` + start : '';
      }
      if (endDate) {
        const end = moment(endDate).format('yyyy-MM-DD') + 'T00:00';
        query += end ? `${query !== '?' ? '&' : ''}endDate=` + end : '';
      }
    }
    return query
  }

  const where = new URLSearchParams(location.search).get("where");
  const startDate = new URLSearchParams(location.search).get("startDate");
  const endDate = new URLSearchParams(location.search).get("endDate");
  const guests = new URLSearchParams(location.search).get("guests");

  return ( 
    <div key={location.key} className="py-20 px-2 sm:px-14 md:px-20 lg:px-32 xl:px-60 bg-silver-accent min-h-screen">
      <SearchAndFilter whereProp={where ? where : ''} guestsProp={guests ? guests : ''} getParams={getParams}
      entityProp={entity} startDateProp={startDate} endDateProp={endDate} totalElements={totalElements}/>
      {entityList.length > 0 &&
      <div className="mt-6">
        <EntityList data={entityList} entityType={entity} currentPage={currentPage} setPage={setCurrentPage} totalPages={totalPages}/>
      </div>}
    </div>
   );
}
 
export default SearchPage;