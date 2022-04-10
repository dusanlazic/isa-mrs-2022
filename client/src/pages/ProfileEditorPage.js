import { useState } from "react";
import MainProfileInfoEditor from "../components/profile_editor/MainProfileInfoEditor";
import PasswordEditor from "../components/profile_editor/PasswordEditor";

const ProfileEditorPage = () => {
  return ( 
    <div className="block min-h-screen p-32 px-8 sm:px-20 md:px-52 lg:px-60 xl:px-96 w-full font-display">

      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg overflow-hidden gap-y-4">
        <MainProfileInfoEditor/>
        <PasswordEditor/>
      </div>

    </div>
   );
}
 
export default ProfileEditorPage;