const PriceCard = ({profileData, loyaltyProgramData}) => {

    if (loyaltyProgramData == null) {
      return (
        <div className="border border-gray-100 rounded-lg text-left
        shadow-sm py-3 px-5 tracking-wide">
          <h1 className="text-xl font-medium text-gray-900">Price</h1>
          <div className="font-mono text-lg">
            <span className="font-bold text-4xl">
              {window.location.href.includes('adventure') ? profileData.pricePerPerson : profileData.pricePerDay}
            </span>
            <span className="text-4xl">
              {profileData.currency}
            </span>
            <span>{window.location.href.includes('adventure') ? "/person" : "/day"}</span>
          </div>
          { !window.location.href.includes('resort') && 
            <div className="text-xs leading-5 mt-5">
              Cancellation fee is <span className="font-mono font-bold text-sm leading-4">{profileData.cancellationFee} {profileData.currency}</span>
            </div>
          }
        </div>
       );
    } else {
      return (
        <div className="border border-gray-100 rounded-lg text-left
        shadow-sm py-3 px-5 tracking-wide">
          <h1 className="text-xl font-medium text-gray-900">Price</h1>
          <div className="font-mono text-gray-400">
            <span className="font-bold text-2xl line-through decoration-3">
              {window.location.href.includes('adventure') ? profileData.pricePerPerson : profileData.pricePerDay}
            </span>
            <span className="text-2xl line-through decoration-3">
              {profileData.currency}
            </span>
          </div>
          <div className="font-mono text-lg text-red-500 -mt-1">
            <span className="font-bold text-4xl">
              { Math.round((window.location.href.includes('adventure') ? profileData.pricePerPerson : profileData.pricePerDay) *
                loyaltyProgramData.category.multiply * 100) / 100
              }
            </span>
            <span className="text-4xl">
              {profileData.currency}
            </span>
            <span>{window.location.href.includes('adventure') ? "/person" : "/day"}</span>
          </div>
          <div className="text-xs leading-5">
              As a <span className='font-bold' style={{color: loyaltyProgramData.category.color}}>{loyaltyProgramData.category.title}</span> user you get
              <span className="font-bold"> {((1 - loyaltyProgramData.category.multiply)*100).toFixed(0)}% </span>
               discount
          </div>
          { !window.location.href.includes('resort') && 
            <div className="text-xs leading-5 mt-5">
              Cancellation fee is <span className="font-mono font-bold text-sm leading-4">{profileData.cancellationFee} {profileData.currency}</span>
            </div>
          }
        </div>
       );
    }
  }
   
  export default PriceCard;