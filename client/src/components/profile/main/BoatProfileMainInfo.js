import { useEffect, useState } from "react"
import { useNavigate } from 'react-router-dom';
import { get, del } from "../../../adapters/xhr";
import { Link } from "react-router-dom";


const BoatProfileMainInfo = ({data, advertisementId}) => {
	let [showMore, setShowMore] = useState(false);
	let description = data.description;
	const [rating, setRating] = useState(null);
	const [showEditBtn, setShowEditBtn] = useState(false);
	const navigate = useNavigate(); 

  const redirectToEdit = () =>{ 
    navigate(`/boat/${advertisementId}/edit`);
  }

	useEffect(() => {
		get(`/api/ads/${data.id}/rating`).then((response) => {
			setRating(response.data);
		  });
		}, [])

	useEffect(() => {
		get(`/api/account/whoami`).then((response) => {
			setShowEditBtn(response.data.id == data.advertiser.id);
		  });
		}, [])

	return (
		<div className="block md:flex bg-gray-100 rounded-lg p-10">
			<div className="block flex-none">
				<img src={data.photos.length > 0 ? `/api/${data.photos[0].uri}` : "/images/boat-placeholder.jpg"} alt="" className="flex-none w-64 h-64 md:w-44 md:h-44 xl:w-52 xl:h-52 object-cover rounded-lg mx-auto" />
					{ showEditBtn && 
					<button className="text-gray-500
					bg-gray-200 hover:bg-gray-300 hover:text-gray-800 active:bg-transparent
					active:bg-gray-400 active:text-gray-50
					rounded-b-lg px-4 h-min text-base md:text-sm xl:text-base" onClick={redirectToEdit}>Edit profile</button> }
			</div>
				
			<div className="flex flex-col flex-grow md:ml-4">

        <div className="block md:flex w-full justify-between text-center md:text-left">
					<h1 className="text-3xl md:text-2xl xl:text-3xl mt-4 mb-1 lg:mb-2 md:mt-1 tracking-tight my-auto">{data.title}</h1>
					<div className="md:hidden text-lg text-center -mt-4">
            <Link to={`/advertiser/${data.advertiser.id}`}>
              <p className="inline-block text-center md:text-left -mt-1 text-sm text-gray-500">
                {data.advertiser.firstName} {data.advertiser.lastName}
              </p>
            </Link>
            <div className="block">
              {rating} <span className="text-yellow-500 text-xl">&#9733;</span>
            </div>
					</div>
				</div>

				<div className="hidden md:block text-lg text-left -mt-4">
          <Link to={`/advertiser/${data.advertiser.id}`} className="inline-block">
            <p className="md:text-left text-sm text-gray-500">
              {data.advertiser.firstName} {data.advertiser.lastName}
            </p>
          </Link>
          <div className="block">
					  {rating} <span className="text-yellow-500 text-xl">&#9733;</span>
          </div>
				</div>

				{/* Description */}
				<div className="block mt-10 md:mt-2 text-left">
					<p className="text-justify text-gray-600 text-base md:text-sm xl:text-base">
						{showMore ? description : description.substring(0, 140)}{!showMore && description.length >= 140 ? '...' : ''}
					</p>

					{description.length >= 140 &&
						<button onClick={() => setShowMore(!showMore)} className="text-sm text-gray-400 hover:text-gray-600">
							{showMore ? 'Show less' : 'Show more'}
						</button>
					}
				</div>

			</div>
		</div>
	);
}

export default BoatProfileMainInfo;