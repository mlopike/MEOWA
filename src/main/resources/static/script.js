/* ФинТабло — shared interactions */

document.addEventListener('DOMContentLoaded', () => {

  /* mobile nav toggle ---------------------------------------------------- */
  const toggle = document.querySelector('.tabs-toggle');
  const tabs = document.querySelector('.tabs');
  if (toggle && tabs) {
    toggle.addEventListener('click', () => {
      const open = tabs.classList.toggle('is-open');
      toggle.setAttribute('aria-expanded', open ? 'true' : 'false');
    });
  }

  /* Банки: live filter ----------------------------------------------------- */
  const bankSearch = document.querySelector('[data-bank-search]');
  const bankRows = document.querySelectorAll('[data-bank-row]');
  const emptyState = document.querySelector('[data-empty-state]');
  const cityChips = document.querySelectorAll('[data-city-chip]');
  let activeCity = 'all';

  function applyBankFilter() {
    if (!bankRows.length) return;
    const q = (bankSearch?.value || '').trim().toLowerCase();
    let visible = 0;
    bankRows.forEach(row => {
      const name = row.getAttribute('data-bank-name') || '';
      const city = row.getAttribute('data-bank-city') || '';
      const matchesText = name.toLowerCase().includes(q);
      const matchesCity = activeCity === 'all' || city === activeCity;
      const show = matchesText && matchesCity;
      row.style.display = show ? '' : 'none';
      if (show) visible++;
    });
    if (emptyState) emptyState.style.display = visible === 0 ? 'block' : 'none';
  }

  if (bankSearch) bankSearch.addEventListener('input', applyBankFilter);
  cityChips.forEach(chip => {
    chip.addEventListener('click', () => {
      cityChips.forEach(c => c.classList.remove('is-active'));
      chip.classList.add('is-active');
      activeCity = chip.getAttribute('data-city-chip');
      applyBankFilter();
    });
  });

  /* Новости: category filter ------------------------------------------- */
  const newsChips = document.querySelectorAll('[data-news-chip]');
  const newsItems = document.querySelectorAll('[data-news-item]');
  newsChips.forEach(chip => {
    chip.addEventListener('click', () => {
      newsChips.forEach(c => c.classList.remove('is-active'));
      chip.classList.add('is-active');
      const cat = chip.getAttribute('data-news-chip');
      newsItems.forEach(item => {
        const itemCat = item.getAttribute('data-category');
        item.style.display = (cat === 'all' || itemCat === cat) ? '' : 'none';
      });
    });
  });

  /* Курс: converter -------------------------------------------------------- */
  const rateTable = { USD: 92.4, EUR: 100.1, CNY: 12.7, BYN: 28.6 };
  const amountFrom = document.querySelector('[data-amount-from]');
  const amountTo = document.querySelector('[data-amount-to]');
  const currFrom = document.querySelector('[data-currency-from]');
  const currTo = document.querySelector('[data-currency-to]');
  const swapBtn = document.querySelector('[data-swap]');
  const rateOut = document.querySelector('[data-rate-out]');

  function localToRub(amount, code) {
    return amount * (rateTable[code] ?? 1);
  }

  function recalc(source) {
    if (!amountFrom || !amountTo || !currFrom || !currTo) return;
    const from = currFrom.value;
    const to = currTo.value;

    if (source === 'to') {
      const valTo = parseFloat(amountTo.value) || 0;
      const rub = to === 'RUB' ? valTo : localToRub(valTo, to);
      const result = from === 'RUB' ? rub : rub / (rateTable[from] ?? 1);
      amountFrom.value = round2(result);
    } else {
      const valFrom = parseFloat(amountFrom.value) || 0;
      const rub = from === 'RUB' ? valFrom : localToRub(valFrom, from);
      const result = to === 'RUB' ? rub : rub / (rateTable[to] ?? 1);
      amountTo.value = round2(result);
    }

    if (rateOut) {
      const oneRub = from === 'RUB' ? 1 : (rateTable[from] ?? 1);
      const display = to === 'RUB' ? oneRub : oneRub / (rateTable[to] ?? 1);
      rateOut.textContent = `1 ${from} = ${round2(display)} ${to}`;
    }
  }

  function round2(n) { return Math.round(n * 100) / 100; }

  if (amountFrom) amountFrom.addEventListener('input', () => recalc('from'));
  if (amountTo) amountTo.addEventListener('input', () => recalc('to'));
  if (currFrom) currFrom.addEventListener('change', () => recalc('from'));
  if (currTo) currTo.addEventListener('change', () => recalc('to'));
  if (swapBtn) {
    swapBtn.addEventListener('click', () => {
      const f = currFrom.value;
      currFrom.value = currTo.value;
      currTo.value = f;
      recalc('from');
    });
  }
  recalc('from');

  /* Курс: sort bank list ---------------------------------------------------- */
  const sortBtn = document.querySelector('[data-sort-rates]');
  const ratesList = document.querySelector('[data-rates-list]');
  let sortAsc = true;
  if (sortBtn && ratesList) {
    sortBtn.addEventListener('click', () => {
      const items = Array.from(ratesList.children);
      items.sort((a, b) => {
        const av = parseFloat(a.getAttribute('data-sell'));
        const bv = parseFloat(b.getAttribute('data-sell'));
        return sortAsc ? av - bv : bv - av;
      });
      items.forEach(i => ratesList.appendChild(i));
      sortAsc = !sortAsc;
      sortBtn.querySelector('span').textContent = sortAsc ? '↓ Дешевле' : '↑ Дороже';
    });
  }
});
