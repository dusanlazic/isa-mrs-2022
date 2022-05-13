import { getToken, getSession } from '../../contexts/'
import { Link } from "react-router-dom";
import { Icon } from '@iconify/react';

const Navbar = () => {
  const isSessionActive = getToken() !== undefined ? true : false;
  const session = getSession();

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
              font-bold rounded-lg px-6 py-1 bg-raisin-black bg-opacity-75
              hover:bg-opacity-100'>
                Register
              </button>
            </Link>

            <Link to="/login">
              <button className=' border-white text-white ml-2
              font-bold rounded-lg px-6 py-1 bg-skobeloff bg-opacity-90
              hover:bg-opacity-100'>
                Login
              </button>
            </Link>

          </div>
        }
        {isSessionActive &&
          // will contain user photo
          <Link to={`/client/${session.id}`}>
            <Icon className="w-10 h-10 text-gray-600 bg-baby-green rounded-full" icon="tabler:user-circle" inline={true} />
          </Link>
        }
      </div>
    </div>
   );
}
 
export default Navbar;