import { Icon } from '@iconify/react';
import { get } from "../adapters/xhr";
import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from "react";

const RegistrationConfirmation = () => {
  const navigate = useNavigate();
  const [ready, setReady] = useState(null);

  useEffect(() => {
  get(`/api/account/register/confirm/${window.location.pathname.split("/").pop()}`)
    .then((response) => {
      setReady(true);
    })
    .catch(error => {
      if (error.status === 400)
        setReady(true);
      else
        navigate("/");
    })
  }, [])

  if (ready === null)
    return null;

  return (
    <div className="block min-h-screen bg-zinc-100">
      <div className="flex items-center justify-center text-teal-500 pt-20">
        <Icon icon="icon-park-outline:success" width={150} />
      </div>

      <div className="text-5xl font-mono font-bold text-teal-500 pt-10 mx-10">Thank you for registering!</div>

      <div className="grid grid-cols-1 md:grid-cols-2 text-2xl font-mono font-thin text-teal-500 pt-10 px-10 md:px-20 lg:px-52 xl:px-60">

        <div className="pt-10 md:border-r-2 md:border-teal-500 px-10">
          <p>You can continue browsing as an unregistered user.</p>
          <Link to="/">
            <button className="bg-teal-500 hover:bg-transparent text-white font-semibold hover:text-teal-500 py-2 px-4 border border-transparent hover:border-teal-500 rounded mt-5">
              Home
            </button>
          </Link>
        </div>

        <div className="pt-10 px-10">
          <p>You can log in as a customer so you can make reservations.</p>
          <Link to="/login">
            <button className="bg-teal-500 hover:bg-transparent text-white font-semibold hover:text-teal-500 py-2 px-4 border border-transparent hover:border-teal-500 rounded mt-5">
              Log in
            </button>
          </Link>
        </div>

      </div>
    </div>
  );
}

export default RegistrationConfirmation;