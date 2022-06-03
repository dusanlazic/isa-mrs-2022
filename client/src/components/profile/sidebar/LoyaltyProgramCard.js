import { useState } from "react";

const LoyaltyProgramCard = ({data}) => {
  const [percentage, setPercentage] = useState(((data.points - data.category.pointsLowerBound) / (data.category.pointsUpperBound - data.category.pointsLowerBound)) * 100);

  return (
    <div className="block border border-gray-100 rounded-lg text-left
    shadow-sm py-3 px-5 tracking-wide">
      <h1 className="text-xl font-medium text-gray-900">Loyalty Program</h1>
      <div class="text-4xl">
        <span className="font-bold" style={{color: data.category.color}}>{data.category.title}</span>
      </div>

      { data.category.pointsUpperBound === 2147483647
      ? <div>
          <div class="text-2xl">
            <span className="font-bold">{data.points}</span>
          </div>

          <div class="w-full bg-gray-200 h-2 mt-2">
            <div className="h-2" style={{
              backgroundColor: data.category.color, 
              width: "100%"
            }}>
            </div>
          </div>
        </div>
      : <div>
          <div class="text-2xl">
            <span className="font-bold">{data.points}</span>
            <span className="text-gray-400">/{data.category.pointsUpperBound + 1}</span>
          </div>

          <div class="w-full bg-gray-200 h-2 mt-2">
            <div className="h-2" style={{
              backgroundColor: data.category.color, 
              width: percentage + "%"
            }}>
            </div>
          </div>
        </div>
        }

      <div className="text-xs leading-5 text-gray-600 mt-2">
        { data.category.multiply < 1 
        ? <div>You get <strong>{((1 - data.category.multiply)*100).toFixed(0)}%</strong> discount on every reservation!</div>
        : <div>You get <strong>{((data.category.multiply - 1)*100).toFixed(0)}%</strong> bonus on every reservation!</div>
        }
      </div>
    </div>
   );
}
 
export default LoyaltyProgramCard;