import { useState, useEffect } from "react";
import { get } from "../adapters/xhr";
import { useNavigate } from 'react-router-dom';

import MainProfileInfoEditor from "../components/profile_editor/MainProfileInfoEditor";
import PasswordEditor from "../components/profile_editor/PasswordEditor";
import RemovalRequestForm from '../components/profile_editor/RemovalRequestForm';


const ProfileEditorPage = () => {
  const [accountData, setAccountData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    getAccountData();
  }, [])

  const getAccountData = () => {
    get(`/api/account`)
    .then((response) => {
      setAccountData(response.data);
    })
    .catch((error) => {
      navigate('/');
    });
  }

  if (accountData === null) {
    return null;
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <MainProfileInfoEditor data={accountData} refreshData={getAccountData}/>
        <PasswordEditor/>
        <RemovalRequestForm id={accountData.id}/>
      </div>

    </div>
   );
}
 
export default ProfileEditorPage;