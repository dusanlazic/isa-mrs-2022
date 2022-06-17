import { getToken, getSession } from '../../contexts/'
import { logout } from '../../util'; 
import { Link } from "react-router-dom";
import { Icon } from '@iconify/react';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

const withoutNavbarRoutes = ["/"];

const Navbar = () => {
  const [isSessionActive, setIsSessionActive] = useState(false);
  const [session, setSession] = useState(undefined);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const {pathname} = useLocation();

  useEffect(() => {
    setIsSessionActive(getToken() !== undefined ? true : false);
    setSession(getSession());
  }, [getSession()])

  const logUserOut = () => {
    logout();
    window.location.reload();
  }

  const handleDropdown = () => {
    if (isDropdownOpen) {
      setIsDropdownOpen(false);
      document.getElementById('dropdown-btn-nav-2').blur();
    } else {
      setIsDropdownOpen(true);
    }
  }


  if (withoutNavbarRoutes.some((item) => pathname === item)) return null;

  if (isSessionActive && session === undefined) {
    return null;
  }

  return ( 
    <div className='fixed top-0 z-50 w-full flex justify-between bg-raisin-black px-3'>
      <Link to="/" className=' my-auto py-2'><img src='/images/logo-v1.png' alt='' className='h-10 my-auto' /></Link>
      <div className='flex'>
        {!isSessionActive &&
          <div className='my-auto'>

            <Link to="/register">
              <button className=' border-white
              font-bold rounded-lg px-6 py-1 text-silver-accent hover:text-slate-300'>
                Register
              </button>
            </Link>

            <Link to="/login">
              <button className=' border-white text-raisin-black ml-2
              font-bold rounded-lg px-6 py-1 bg-silver-accent hover:bg-slate-300'>
                Login
              </button>
            </Link>

          </div>
        }
        {isSessionActive &&
          <div className='dropdown dropdown-end my-auto'>
            <label tabIndex="0" className='btn' id="dropdown-btn-nav-2" onClick={() => handleDropdown()}
            onBlur={e => setIsDropdownOpen(false)}>
              <Icon className="w-10 h-10 text-gray-600 bg-silver-accent rounded-full" icon="tabler:user-circle" inline={true} />
            </label>
            <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-silver-accent text-left rounded-box w-40">
              <Link to={`/me`}>
                <li>
                  <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                    <Icon className='w-6 h-6 text-gray-600' icon="tabler:user" inline={true} />
                    <span className='ml-1.5 pb-1'>profile</span>
                  </div>
                </li>
              </Link>

              <Link to='/account'>
                <li>
                  <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                    <Icon className='w-6 h-6 text-gray-600' icon="tabler:pencil" inline={true} />
                    <span className='ml-1.5 pb-1'>edit account</span>
                  </div>
                </li>
              </Link>

              { (session.accountType == "FISHING_INSTRUCTOR_OWNER" || session.accountType == "RESORT_OWNER" || session.accountType == "BOAT_OWNER" ) &&
                
              <Link to='/control-panel'>
                <li>
                  <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                    <Icon className='w-6 h-6 text-gray-600' icon="tabler:settings" inline={true} />
                    <span className='ml-1.5 pb-1'>control panel</span>
                  </div>
                </li>
              </Link>
              }

              <li onClick={logUserOut}>
                <div className='flex text-lg rounded-lg hover:bg-gray-500 hover:bg-opacity-10 px-1'>
                  <Icon className='w-6 h-6 text-gray-600' icon="tabler:logout" inline={true} />
                  <span className='ml-1.5 pb-1'>log out</span>
                </div>
              </li>

            </ul>

          </div>
        }
      </div>
    </div>
   );
}
 
export default Navbar;