import { post } from "../xhr";

export function registerCustomer(data, setIsRegistrationSuccessful, setError) {
  post('/api/account/register/customer', 
  {
    firstName: data.firstName,
    lastName: data.lastName,
    username: data.email,
    password: data.password,
    passwordConfirmation: data.confirmPassword,
    address: data.address,
    city: data.city,
    countryCode: data.selectedCountry,
    phoneNumber: data.phoneNumber
  })
  .then(res => {
    setIsRegistrationSuccessful(true);
  })
  .catch(err => {
    if (err.response.data.message) {
      setError(err.response.data.message);
    }
  })
}

export function registerAdvertiser(data, setIsRegistrationSuccessful, setError) {
  let subrole;
  if (data.selectedSubrole === 'resort-owner') subrole = 2;
  else if (data.selectedSubrole === 'boat-owner') subrole = 3;
  else subrole = 4;
  post('/api/account/register/advertiser', 
  {
    firstName: data.firstName,
    lastName: data.lastName,
    username: data.email,
    password: data.password,
    passwordConfirmation: data.confirmPassword,
    address: data.address,
    city: data.city,
    countryCode: data.selectedCountry,
    phoneNumber: data.phoneNumber,
    accountType: subrole,
    explanation: data.reason
  })
  .then(res => {
    setIsRegistrationSuccessful(true);
  })
  .catch(err => {
    if (err.response.data.message) {
      setError(err.response.data.message);
    }
  })
}