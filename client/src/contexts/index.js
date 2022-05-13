

export const getSession = () => {
  try {
    const serializedState = localStorage.getItem('session');
    if (serializedState === null) {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
}

export const saveSession = (state) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem('session', serializedState);
  } catch (err) {
    // rest in peace
  }
}

export const invalidateSession = () => {
  localStorage.removeItem('session');
}

export const getToken = () => {
  try {
    const token = localStorage.getItem('token');
    if (token === null) {
      return undefined;
    }
    return token
  } catch (err) {
    return undefined;
  }
}

export const saveToken = (token) => {
  try {
    localStorage.setItem('token', token);
  } catch (err) {
    // rest in peace
  }
}

export const invalidateToken = () => {
  localStorage.removeItem('token');
}
