// Theme

const getStoredTheme = () => localStorage.getItem('theme')
const setStoredTheme = theme => localStorage.setItem('theme', theme)

const getPreferredTheme = () => {
	const storedTheme = getStoredTheme()
	if (storedTheme) {
		return storedTheme
	}

	return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'
}

const setTheme = theme => {
	if (theme === 'auto') {
		document.documentElement.setAttribute('data-bs-theme', (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light'))
	} else {
		document.documentElement.setAttribute('data-bs-theme', theme)
	}
}

setTheme(getPreferredTheme())

window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', () => {
	const storedTheme = getStoredTheme()
	if (storedTheme !== 'light' && storedTheme !== 'dark') {
		setTheme(getPreferredTheme())
	}
})

window.addEventListener('DOMContentLoaded', () => {
	document.querySelector("#theme__switch").checked = getPreferredTheme() == "dark"
	document.querySelector("#theme__switch").addEventListener('change', function(ev) {
		if (ev.target.checked) {
			setTheme("dark")
		} else {
			setTheme("light")
		}
	})
})

// End Theme