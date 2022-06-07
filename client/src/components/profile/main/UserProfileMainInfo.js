import { Link } from 'react-router-dom';
import { Icon } from '@iconify/react';
import { getSession } from '../../../contexts';

const UserProfileMainInfo = ({data}) => {
  return ( 
    <div className="block md:flex bg-gray-100 rounded-lg p-10 pb-10">
      <div>
        <img src={data.avatar.uri !== null ? `/api${data.avatar.uri}` : '/images/fish_guy.jpg'} alt="" className="flex-none w-64 h-64 md:w-44 md:h-44 xl:w-52
        xl:h-52 object-cover rounded-lg mx-auto" />
        {getSession() != null && data.id === getSession().id &&
        <Link to='/account'>
          <button className="text-gray-500
          bg-gray-200 hover:bg-gray-300 hover:text-gray-800 active:bg-transparent
          active:bg-gray-400 active:text-gray-50
          rounded-b-lg px-4 h-min text-base md:text-sm xl:text-base">
            Edit Profile
          </button>
        </Link>
        }
      </div>

      <div className="flex flex-col justify-between flex-grow md:ml-4">

        <div>
          <div className="block md:flex w-full justify-between text-center md:text-left">
            <h1 className="text-3xl md:text-2xl xl:text-3xl mt-4 mb-1 lg:mb-2 md:mt-1 tracking-tight my-auto">{data.firstName} {data.lastName}</h1>
            <div className="block md:hidden -mt-1 text-sm w-20 bg-yellow-400 rounded-xl select-none mx-auto mb-4">
              Gold
            </div>
            
          </div>

          <div className="hidden md:block -mt-1 text-sm w-20 bg-yellow-400 rounded-xl select-none">
            Gold
          </div>

          {/* Basic info */}
          <div className='flex justify-center md:justify-start mt-10 md:mt-4 '>
            <div className="flex flex-col grid-cols-1 text-center md:text-left text-gray-600 gap-y-0.5">
              <div className='flex gap-x-2'>
                <Icon className='my-auto text-2xl text-gray-500' icon="tabler:map-pin" inline={true} />
                <p className='my-auto'>{data.city}, {data.countryCode}</p>
              </div>
              <div className='flex gap-x-2'>
                <Icon className='my-auto text-2xl text-gray-500' icon="tabler:phone" inline={true} />
                <p className='my-auto'>{data.phoneNumber}</p>
              </div>
              <div className='flex gap-x-2'>
                <Icon className='my-auto text-2xl text-gray-500' icon="tabler:mail" inline={true} />
                <p className='my-auto'>{data.username}</p>
              </div>
            </div>
          </div>

        </div>

      </div>
    </div>
   );
}
 
export default UserProfileMainInfo;