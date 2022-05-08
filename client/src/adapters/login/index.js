import { post } from "../xhr";
import { saveToken } from '../../contexts'

export function login(data, redirect, setError) {
  post('/api/auth/login', 
  {
    username: data.email,
    password: data.password,
  })
  .then(res => {
    if (res.data) {
      saveToken(res.data.accessToken);
      redirect();
    }
  })
  .catch((err) => {
    console.log(err.response.data.message);
    if (err.response.data.message) {
      setError(err.response.data.message);
    }
  })
}