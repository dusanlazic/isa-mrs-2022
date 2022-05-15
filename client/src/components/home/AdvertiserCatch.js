import { Link } from "react-router-dom";

const AdvertiserCatch = () => {
  return ( 
    <div className="py-11 w-full px-8 lg:px-28 xl:px-40
    text-left bg-slate-100 shadow-sm">
      <div className="text-lg font-medium">Promote your services with us</div>
      <div className="text-xs">Become an advertiser</div>

      <div className="grid grid-cols-1 sm:grid-cols-3 grid-rows-1 w-full mt-8
      font-display gap-y-7 sm:gap-y-0 sm:gap-x-4">
        <div className="w-full mx-auto">
          <img src="/images/cottage-2.jpg" alt=""
          className="h-40 w-full mx-auto object-cover rounded-lg"/>
          <div className="text-sm mt-2">
            Do you manage a vacation home or a cottage?
          </div>
          <div className="text-xs text-slate-500 mt-0.5">
          <b className="hover:text-slate-600">
            <Link to='/register'>Join our network</Link>
          </b> and showcase your property to millions of users, gain perks and earn more.
          </div>

        </div>

        <div className="w-full mx-auto">
          <img src="/images/boat-1.jpg" alt=""
          className="h-48 sm:h-40 w-full mx-auto object-cover rounded-lg"/>
          <div className="text-sm mt-2">
            Do you offer boat renting services?
          </div>
          <div className="text-xs text-slate-500 mt-0.5">
          <b className="hover:text-slate-600">
            <Link to='/register'>Join our network</Link>
          </b> and get in direct contact with adventure-loving people.
          </div>

        </div>

        <div className="w-full mx-auto">
          <img src="/images/fisherman-1.jpg" alt=""
          className="h-40 w-full mx-auto object-cover rounded-lg"/>
          <div className="text-sm mt-2">
            Are you a certified fishing instructor?
          </div>
          <div className="text-xs text-slate-500 mt-0.5">
            Make the most out of your talent by
            <b className="hover:text-slate-600">
              <Link to='/register'> joining our network</Link>
            </b>.
          </div>

        </div>
      </div>

    </div>
   );
}
 
export default AdvertiserCatch;