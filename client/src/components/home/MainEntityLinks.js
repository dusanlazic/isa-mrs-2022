import { Link } from "react-router-dom";

const MainEntityLinks = () => {
  return ( 
    <div className="py-14 w-full px-8 lg:px-28 xl:px-40 font-opensans
    text-xl sm:text-2xl md:text-4xl font-medium text-left bg-white shadow-sm">

      <div className="text-lg font-medium font-sans">Find what your heart desires</div>
      <div className="text-xs">Pick your adventure</div>

      <div className="grid grid-cols-2 grid-rows-2 h-64 sm:h-80 md:h-120 gap-x-4 mx-auto mt-5">
        
          <div class="relative row-span-2 overflow-hidden h-full w-full">
            <Link to='/' className="group">
              <img src="/images/fishing-rod-small.jpg" alt="" 
              className="h-full w-full object-cover rounded-2xl lg:rounded-3xl transition
              duration-200 ease-in-out group-hover:scale-110"/>
              <div 
              class="absolute w-full py-3 bottom-0 inset-x-0 text-white px-4
              text-left">
                Learn <br/> from the best
              </div>
            </Link>
          </div>

        <div className="grid grid-rows-2 row-span-2 h-full w-full gap-y-4">
          <Link to='/' className="group overflow-hidden">
            <div className="relative overflow-hidden h-full w-full">
              <img src="/images/blue-cabin.jpg" alt=""  
              className="h-full w-full object-cover rounded-2xl lg:rounded-3xl transition
              duration-200 ease-in-out group-hover:scale-110"/>
              <div className="absolute h-full w-full bottom-0 text-white
              bg-raisin-black bg-opacity-10 rounded-2xl lg:rounded-3xl group-hover:rounded-none">
                <div className='absolute bottom-0 pb-3 px-4'>Enjoy life</div>
              </div>
            </div>
          </Link>
          

          <Link to='/' className="group overflow-hidden">
            <div className="relative overflow-hidden h-full w-full">
              <img src="/images/boat.jpg" alt=""  
              className="h-full w-full object-cover rounded-2xl lg:rounded-3xl transition
              duration-200 ease-in-out group-hover:scale-110"/>
              <div className="absolute h-full w-full bottom-0 text-white
              bg-raisin-black bg-opacity-10 rounded-2xl lg:rounded-3xl group-hover:rounded-none">
                <div className='absolute bottom-0 pb-3 px-4'>Embrace <br /> your spirit</div>
              </div>
            </div>
          </Link>

        </div>

      </div>
    </div>
   );
}
 
export default MainEntityLinks;