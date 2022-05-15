import { useState, useEffect } from "react";
import { get } from "../../adapters/xhr";
import { Icon } from "@iconify/react";
import { Link } from "react-router-dom";

let called = [];

const TopTens = () => {
  const [current, setCurrent] = useState('resorts');
  const [data, setData] = useState(
    {
      'resorts': null,
      'boats': null,
      'adventures': null
    }
  );
  const [currentData, setCurrentData] = useState(null);

  useEffect(() => {
    if (!called.includes(current)) {
      getData();
    } else {
      setCurrentData(data[current]);
    }
  }, [current])

  const getData = () => {
    get(`/api/ads/${current}/top10`)
      .then((response) => {
        console.log(response.data);
        called.push(current);
        let new_data = data;
        new_data[current] = response.data;
        setData(new_data);
        setCurrentData(data[current]);
      })
  }

  const getPlaceholderImage = (what) => {
    if (what === 'resorts') return '/images/cottage-2.jpg'
    if (what === 'boats') return '/images/boat-1.jpg'
    if (what === 'adventures') return '/images/fisherman-1.jpg'
  }

  if (!currentData) {
    return null;
  }

  return ( 
    <div className="py-11 w-full px-8 lg:px-28 xl:px-40
    text-left bg-gradient-to-b from-stone-50 to-white shadow-sm">

      <div className="flex justify-between">
        <div className="text-lg font-medium">Our recommendations</div>
        <div className="hidden sm:flex gap-x-2">
          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'resorts' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:window" inline={true} onClick={() => setCurrent('resorts')}/>

          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'boats' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:sailboat" inline={true} onClick={() => setCurrent('boats')}/>

          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'adventures' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:fish" inline={true} onClick={() => setCurrent('adventures')}/>
        </div>
      </div>
      <div className="text-xs">Explore the best we have to offer</div>
      <div className="sm:hidden flex gap-x-2 mt-2">
          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'resorts' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:window" inline={true} onClick={() => setCurrent('resorts')}/>

          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'boats' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:sailboat" inline={true} onClick={() => setCurrent('boats')}/>

          <Icon className={`my-auto w-6 h-6 cursor-pointer
          ${current === 'adventures' ? 'text-gray-800' : 'text-gray-500 hover:text-gray-700'}`}
          icon="tabler:fish" inline={true} onClick={() => setCurrent('adventures')}/>
        </div>

      <div className="grid grid-rows-3 grid-cols-2 md:grid-rows-2 md:grid-cols-3 w-full mt-8
      font-display gap-y-4 gap-x-2 sm:gap-x-4">
        {currentData.slice(0, 6).map(entity => 

          <div key={entity.id} className="w-full mx-auto">
            <Link to={`/${current.substring(0, current.length - 1)}/${entity.id}`}>
              <img src={entity.photos.length > 0 ? `/api/${entity.photos[0].uri}` : getPlaceholderImage(current)} alt=""
              className="h-32 sm:h-40 w-full mx-auto object-cover rounded-lg"/>
            </Link>

            <div className="flex text-xs mt-2">
              <Icon className='text-green-700 my-auto' icon="tabler:star" inline={true} />
              <div className="ml-1 pt-0.5">4.75 (254)</div>
            </div>

            <div className="text-sm mt-0.5">
              <Link to={`/${current.substring(0, current.length - 1)}/${entity.id}`}>
                {entity.title}
              </Link>
            </div>
            
            <div className="text-xs text-slate-500 mt-0.5">
              {entity.description}
            </div>
          </div>

          )}
      </div>
    </div>
   );
}
 
export default TopTens;