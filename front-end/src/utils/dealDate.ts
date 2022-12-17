export function dealDate(date: string | Date | number) {
  if (date instanceof Date) {
    return date.toLocaleDateString('CCT')
  } else if (typeof date === 'number') {
    return (new Date(date)).toLocaleDateString('CCT')
  }
  return date
}