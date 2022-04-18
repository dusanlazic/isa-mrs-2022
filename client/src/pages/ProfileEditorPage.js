import { useState, useEffect } from "react";
import { get } from "../adapters/xhr";

import MainProfileInfoEditor from "../components/profile_editor/MainProfileInfoEditor";
import PasswordEditor from "../components/profile_editor/PasswordEditor";

const ProfileEditorPage = () => {
  const [accountData, setAccountData] = useState(null);

  useEffect(() => {
    // get(`/api${endpoint}/${id}`)
    get(`/api/account/1`)
    .then((response) => {
      setAccountData(response.data);
    });
  }, [])

  if (accountData === null) {
    return null;
  }

  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <MainProfileInfoEditor data={accountData}/>
        <PasswordEditor/>
      </div>

    </div>
   );
}
 
export default ProfileEditorPage;