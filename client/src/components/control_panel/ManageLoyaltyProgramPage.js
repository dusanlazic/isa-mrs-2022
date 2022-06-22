import { useState, useEffect } from "react";
import { get, put } from "../../adapters/xhr";
import { useNavigate } from 'react-router-dom';
import { Icon } from "@iconify/react";

import MessageModal from "../modals/MessageModal";

const ManageLoyaltyProgramPage = () => {
  const [clientScorePerReservation, setClientScorePerReservation] = useState(null);
  const [advertiserScorePerReservation, setAdvertiserScorePerReservation] = useState(null);
  const [customerCategoriesInputFields, setCustomerCategoriesInputFields] = useState(null);
  const [advertiserCategoriesInputFields, setAdvertiserCategoriesInputFields] = useState(null);

  const navigate = useNavigate();
  const [errors, setErrors] = useState(null);

  const [deleteCategories, setDeleteCategories] = useState([])

  const [showMessageModal, setShowMessageModal] = useState(false);
  const [messageModalText, setMessageModalText] = useState('');
  
  
  useEffect(() => {
    get(`/api/admin/system/loyalty/settings`)
    .then(response => {
      setClientScorePerReservation(response.data.clientScorePerReservation);
      setAdvertiserScorePerReservation(response.data.advertiserScorePerReservation);
    })
    .catch(error => {
      navigate('/');
    });

    get(`/api/admin/system/loyalty/categories?type=customer`)
    .then(response => {
      setCustomerCategoriesInputFields(response.data.map((item) => {
        return {
          id: item.id,
          title: item.title,
          color: item.color,
          points: item.pointsLowerBound,
          discount: (100 - (item.multiply * 100)).toFixed(2),
        }
      }));
    })
    .catch(error => {
      navigate('/');
    });

    get(`/api/admin/system/loyalty/categories?type=advertiser`)
    .then(response => {
      setAdvertiserCategoriesInputFields(response.data.map((item) => {
        return {
          id: item.id,
          title: item.title,
          color: item.color,
          points: item.pointsLowerBound,
          bonus: ((item.multiply * 100) - 100).toFixed(2)
        }
      }));
    })
    .catch(error => {
      navigate('/');
    });
  }, [])

  const handleCustomerCategoriesChange = (index, event) => {
    let data = [...customerCategoriesInputFields];
    data[index][event.target.name] = event.target.value;
    setCustomerCategoriesInputFields(data);
  }

  const addCustomerCategoryField = () => {
    let newField = { title: '', color: '', points: '', discount: '' }
    setCustomerCategoriesInputFields([...customerCategoriesInputFields, newField]);
  }

  const deleteCustomerCategoryField = (index) => {
    if (customerCategoriesInputFields[index].hasOwnProperty('id')) {
      setDeleteCategories([...deleteCategories, customerCategoriesInputFields[index].id])
    }
    
    let data = [...customerCategoriesInputFields];
    data.splice(index, 1);
    setCustomerCategoriesInputFields(data);
  }

  const handleAdvertiserCategoriesChange = (index, event) => {
    let data = [...advertiserCategoriesInputFields];
    data[index][event.target.name] = event.target.value;
    setAdvertiserCategoriesInputFields(data);
  }

  const addAdvertiserCategoryField = () => {
    let newField = { title: '', color: '', points: '', discount: '' }
    setAdvertiserCategoriesInputFields([...advertiserCategoriesInputFields, newField]);
  }

  const deleteAdvertiserCategoryField = (index) => {
    if (advertiserCategoriesInputFields[index].hasOwnProperty('id')) {
      setDeleteCategories([...deleteCategories, advertiserCategoriesInputFields[index].id])
    }

    let data = [...advertiserCategoriesInputFields];
    data.splice(index, 1);
    setAdvertiserCategoriesInputFields(data);
  }

  const saveCategories = () => {
    let categories = [];

    for (let index = 0; index < customerCategoriesInputFields.length; ++index) {
      const category = customerCategoriesInputFields[index];
      
      let upper = 2147483647 
      if (index !== (customerCategoriesInputFields.length - 1)) {
        const nextCategory = customerCategoriesInputFields[index+1];
        upper = nextCategory.points - 1;
      }

      categories.push({
        id: category.id,
        title: category.title,
        color: category.color,
        targetedAccountType: "CUSTOMER",
        pointsLowerBound: category.points,
        pointsUpperBound: upper,
        multiply: (1 - (category.discount / 100)).toFixed(2)
      });
    }

    for (let index = 0; index < advertiserCategoriesInputFields.length; ++index) {
      const category = advertiserCategoriesInputFields[index];
      
      let upper = 2147483647 
      if (index !== (advertiserCategoriesInputFields.length - 1)) {
        const nextCategory = advertiserCategoriesInputFields[index+1];
        upper = nextCategory.points - 1;
      }

      categories.push({
        id: category.id,
        title: category.title,
        color: category.color,
        targetedAccountType: "ADVERTISER",
        pointsLowerBound: category.points,
        pointsUpperBound: upper,
        multiply: (1 + (category.bonus / 100)).toFixed(2)
      });
    }

    put("/api/admin/system/loyalty/categories", {
      categories: categories,
      delete: deleteCategories
    })
    .then((response) => {
      setMessageModalText('Categories successfully updated!');
      setShowMessageModal(true);
    })
    .catch((error) => {
      setMessageModalText(`Error: ${error.response.data.message}`);
      setShowMessageModal(true);
    });
  }

  const saveConfiguration = () => {
    put("/api/admin/system/loyalty/settings", {
      clientScorePerReservation: clientScorePerReservation,
      advertiserScorePerReservation: advertiserScorePerReservation
    })
    .then((response) => {
      setMessageModalText('Scores successfully updated!');
      setShowMessageModal(true);
    })
    .catch((error) => {
      setMessageModalText(`Error: ${error.response.data.message}`);
      setShowMessageModal(true);
    });
  }

  if (customerCategoriesInputFields == null || advertiserCategoriesInputFields == null) {
    return
  }

  return ( 
    <div>
      <div className="flex flex-col divide-y divide-dashed
       h-full border-0.5 rounded-lg gap-y-4">
        <div className="block w-full">
          <h1 className="text-2xl text-left text-gray-400 font-sans">Configure loyalty program</h1>

          {/* Points per reservation */}
          <h2 className="flex text-xl text-left text-gray-800 font-sans mt-6 pt-6">
            <Icon className="mr-2" icon="tabler:flame" inline={true} fontSize={30} />
            <span>Points per reservation</span>
          </h2>

          <div className="grid grid-cols-6 mt-2 gap-x-6">
            <div className="block col-span-2 text-left">
              <label className="text-xs">For clients</label>
              <input type="number"
              value={clientScorePerReservation}
              onChange={(event) => {setClientScorePerReservation(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.clientScorePerReservation}</p>
              </div>
            </div>

            <div className="block col-span-2 text-left">
              <label className="text-xs">For advertisers</label>
              <input type="number"
              value={advertiserScorePerReservation}
              onChange={(event) => {setAdvertiserScorePerReservation(event.target.value)}}
              className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
              focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
              <div className="h-2">
                <p className="text-xs text-red-500 my-0 tracking-wide">{errors === null ? '' : errors.lastName}</p>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-6 mt-2">
            <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5"
              onClick={() => {saveConfiguration()}}>
                Save configuration
            </button>
          </div>

          {/* Categories */}
          <h2 className="flex text-xl text-left text-gray-800 font-sans mt-6 pt-6">
            <Icon className="mr-2" icon="tabler:medal-2" inline={true} fontSize={30} />
            <span>Loyalty program categories</span>
          </h2>
          <h2 className="flex text-xl text-left text-gray-500 font-sans font-bold pt-4">
            <span>Customers</span>
          </h2>

          <div className="grid grid-cols-12 mt-4 gap-x-3">
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Title</span>
            </div>
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Color</span>
            </div>
            <div className="block col-span-1 text-left text-m font-bold">
              <span>Points</span>
            </div>
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Discount (%)</span>
            </div>
          </div>

          {customerCategoriesInputFields.map((input, index) => {
            return (
              <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
                <div className="block col-span-2 text-left">
                  <input placeholder="category title"
                    name="title"
                    value={input.title}
                    onChange={event => handleCustomerCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-2 text-left">
                  <input name="color"
                    type="color"
                    value={input.color}
                    onChange={event => handleCustomerCategoriesChange(index, event)}
                    className="block rounded-lg px-1 border text-gray-700 border-gray-300 text-base py-1
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-1 text-left">
                  <input placeholder="points required"
                    name="points"
                    type="number"
                    min="0"
                    value={input.points}
                    onChange={event => handleCustomerCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-2 text-left">
                  <input placeholder="discount"
                    name="discount"
                    value={input.discount}
                    onChange={event => handleCustomerCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>
                <button onClick={() => deleteCustomerCategoryField(index)}>
                  <Icon className="mr-2 text-gray-400 hover:text-gray-800" icon="tabler:trash" inline={true} fontSize={30} />
                </button>
              </div>
            )
          })}

          <div className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-1">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <button onClick={addCustomerCategoryField}>
              <Icon className="mr-2 text-gray-400 hover:text-green-500" icon="tabler:plus" inline={true} fontSize={30} />
            </button>
          </div>

          <h2 className="flex text-xl text-left text-gray-500 font-sans font-bold pt-6">
            <span>Advertisers</span>
          </h2>

          <div className="grid grid-cols-12 mt-4 gap-x-3">
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Title</span>
            </div>
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Color</span>
            </div>
            <div className="block col-span-1 text-left text-m font-bold">
              <span>Points</span>
            </div>
            <div className="block col-span-2 text-left text-m font-bold">
              <span>Bonus (%)</span>
            </div>
          </div>

          {advertiserCategoriesInputFields.map((input, index) => {
            return (
              <div key={index} className="grid grid-cols-12 mt-1 gap-x-3">
                <div className="block col-span-2 text-left">
                  <input placeholder="category title"
                    name="title"
                    value={input.title}
                    onChange={event => handleAdvertiserCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-2 text-left">
                <input name="color"
                    type="color"
                    value={input.color}
                    onChange={event => handleAdvertiserCategoriesChange(index, event)}
                    className="block rounded-lg px-1 border text-gray-700 border-gray-300 text-base py-1
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-1 text-left">
                  <input placeholder="points required"
                    name="points"
                    type="number"
                    min="0"
                    value={input.points}
                    onChange={event => handleAdvertiserCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>

                <div className="block col-span-2 text-left">
                  <input placeholder="bonus"
                    name="bonus"
                    value={input.bonus}
                    onChange={event => handleAdvertiserCategoriesChange(index, event)}
                    className="block rounded-lg px-3 border text-gray-700 border-gray-300 text-base py-2
                  focus:outline-none focus:border-gray-500 w-full caret-gray-700"/>
                </div>
                <button onClick={() => deleteAdvertiserCategoryField(index)}>
                  <Icon className="mr-2 text-gray-400 hover:text-gray-800" icon="tabler:trash" inline={true} fontSize={30} />
                </button>
              </div>
            )
          })}

          <div className="grid grid-cols-12 mt-1 gap-x-3">
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-1">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <div className="block col-span-2">
              <input disabled={true} className="block rounded-lg px-3 border border-gray-300 bg-gray-100 py-2 w-full"/>
            </div>
            <button onClick={addAdvertiserCategoryField}>
              <Icon className="mr-2 text-gray-400 hover:text-green-500" icon="tabler:plus" inline={true} fontSize={30} />
            </button>
          </div>


          <div className="grid grid-cols-6">
            <button className="bg-teal-600 hover:bg-teal-700 active:bg-teal-800 drop-shadow-md
              text-white rounded-lg py-2.5 lg:py-2 text-sm lg:text-base mb-1.5 mt-6"
              onClick={saveCategories}>
                Save categories
            </button>
          </div>
        </div>
      </div>

      {showMessageModal &&
      <MessageModal  closeFunction = {() => setShowMessageModal(false)} text = { messageModalText }
      />}

    </div>
   );
}
 
export default ManageLoyaltyProgramPage;