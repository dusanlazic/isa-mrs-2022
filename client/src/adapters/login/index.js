import { get, post } from "../xhr";
import { saveSession, saveToken } from '../../contexts'

export function login(data, redirect, setError) {
  post('/api/auth/login', 
  {
    username: data.email,
    password: data.password,
  })
  .then(response => {
    if (response.data) {
      saveToken(response.data.accessToken);
      getWhoAmI(redirect);
    }
  })
  .catch((err) => {
    if (err.response.data.message) {
      setError(err.response.data.message);
    }
  })
}

export function getWhoAmI(redirect=undefined) {
  get('/api/account/whoami')
  .then(response => {
    saveSession(response.data);
    if (redirect) redirect();
  })
}