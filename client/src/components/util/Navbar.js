import { getToken, getSession } from '../../contexts/'
import { logout } from '../../util'; 
import { Link } from "react-router-dom";
import { Icon } from '@iconify/react';
import { useEffect, useState } from 'react';

const Navbar = () => {
  const [isSessionActive, setIsSessionActive] = useState(false);
  const [session, setSession] = useState(undefined);

  useEffect(() => {
    setIsSessionActive(getToken() !== undefined ? true : false);
    setSession(getSession());
  }, [])

  const logUserOut = () => {
    logout();
    window.location.reload();
  }

  if (isSessionActive && session === undefined) {
    return null;
  }
  return ( 
    <div className='flex justify-between py-4 px-4'>
      <Link to="/"><img src='/images/logo-v1.png' alt='' className='h-10' /></Link>
      <div className='flex'>
        {!isSessionActive &&
          <div>

            <Link to="/register">
              <button className=' border-white text-white
              font-bold rounded-lg px-6 py-1 bg-raisin-black text-baby-green
              hover:bg-opacity-100'>
                Register
              </button>
            </Link>

            <Link to="/login">
              <button className=' border-white text-raisin-black ml-2
              font-bold rounded-lg px-6 py-1 bg-baby-green
              hover:bg-opacity-100'>
                Login
              </button>
            </Link>

          </div>
        }
        {isSessionActive &&
          <div className='dropdown dropdown-end'>
            <label tabIndex="0" className='btn'>
              <Icon className="w-10 h-10 text-gray-600 bg-baby-green rounded-full" icon="tabler:user-circle" inline={true} />
            </label>
            <ul tabIndex="0" className="dropdown-content menu p-2 shadow bg-baby-green text-left rounded-box w-40">
              <Link to={`/client/${session.id}`}>
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